package com.usadapekora.bot.discordbot.event

import com.usadapekora.bot.application.guild.find.GuildFinder
import com.usadapekora.bot.application.trigger.find.TriggerFinder
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFinder
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.infraestructure.trigger.prometheus.registerTriggerFired
import com.usadapekora.shared.domain.LoggerFactory
import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.reactor.awaitSingle
import org.koin.java.KoinJavaComponent.inject

private val finder: TriggerFinder by inject(TriggerFinder::class.java)
private val guildFinder: GuildFinder by inject(GuildFinder::class.java)
private val triggerTextResponseFinder: TriggerTextResponseFinder by inject(TriggerTextResponseFinder::class.java)
private val loggerFactory: LoggerFactory by inject(LoggerFactory::class.java)
private val logger = loggerFactory.getLogger("com.usadapekora.bot.discordbot.event.HandleTriggerKt")

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

    /* TODO: get trigger source audio and play through lavaplayer
    val stream = object {}::class.java.getResourceAsStream("/hahahahaha.mp3")!!
    val destination = Path(storageDirPath, "cache", "hahahahaha.mp3").toAbsolutePath()

    if (!destination.toFile().exists()) {
        Files.copy(stream, destination)
    }

    playAudioOnSenderVoiceChannel(destination.toString())
    */

    registerTriggerFired()
    return true
}
