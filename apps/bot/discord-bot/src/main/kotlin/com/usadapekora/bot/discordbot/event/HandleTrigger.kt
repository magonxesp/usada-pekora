package com.usadapekora.bot.discordbot.event

import com.usadapekora.bot.application.guild.find.GuildFinder
import com.usadapekora.bot.application.trigger.find.TriggerFinder
import com.usadapekora.bot.application.trigger.find.audio.TriggerAudioResponseFinder
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFinder
import com.usadapekora.bot.discordbot.voice.playAudioOnSenderVoiceChannel
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.infraestructure.trigger.prometheus.registerTriggerFired
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.storageDirPath
import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.reactor.awaitSingle
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

private val finder: TriggerFinder by inject(TriggerFinder::class.java)
private val guildFinder: GuildFinder by inject(GuildFinder::class.java)
private val triggerTextResponseFinder: TriggerTextResponseFinder by inject(TriggerTextResponseFinder::class.java)
private val triggerAudioResponseFinder: TriggerAudioResponseFinder by inject(TriggerAudioResponseFinder::class.java)
private val loggerFactory: LoggerFactory by inject(LoggerFactory::class.java)
private val logger = loggerFactory.getLogger("com.usadapekora.bot.discordbot.event.HandleTriggerKt")

/**
 * Prepare the audio source and returns the path or url of the audio source
 */
fun prepareAudioSource(source: String, kind: String): String = when (kind.uppercase()) {
    TriggerAudioResponse.TriggerAudioResponseKind.RESOURCE.name -> {
        val stream = object {}::class.java.getResourceAsStream(source)!!
        val cacheDirectory = Path(storageDirPath, "cache").toAbsolutePath()

        if (Files.notExists(cacheDirectory)) {
            Files.createDirectory(cacheDirectory)
        }

        val destination = Path(storageDirPath, "cache", File(source).name).toAbsolutePath()

        if (!destination.toFile().exists()) {
            Files.copy(stream, destination)
        }

        destination.toString()
    }
    else -> source
}

suspend fun MessageCreateEvent.handleTrigger(): Boolean {
    val channel = message.channel.awaitSingle()
    val guildId = message.guildId.get().asString()
    val guild = guildFinder.findByProviderId(guildId, GuildProvider.DISCORD.value).onLeft {
        logger.warning(it.message ?: "Not found Discord guild with id $guildId")
        return false
    }.getOrNull()!!

    val trigger = finder.findByInput(message.content, guild.id()).onLeft {
        logger.warning(it.message ?: "Not found trigger matching input ${message.content}")
        return false
    }.getOrNull()!!

    if (trigger.responseTextId != null) {
        triggerTextResponseFinder.find(trigger.responseTextId!!).onLeft {
            logger.warning(it.message ?: "Not found trigger text response with id ${trigger.responseTextId}")
        }.onRight {
            channel.createMessage(it.content).awaitSingle()
        }
    }

    if (trigger.responseAudioId != null) {
        triggerAudioResponseFinder.find(trigger.responseAudioId!!).onLeft {
            logger.warning(it.message ?: "Not found trigger audio response with id ${trigger.responseAudioId}")
        }.onRight {
            val preparedSource = prepareAudioSource(it.source, it.kind)
            playAudioOnSenderVoiceChannel(preparedSource)
        }
    }

    registerTriggerFired()
    return true
}
