package com.usadapekora.bot.discordbot.voice

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.usadapekora.shared.domain.LoggerFactory
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.`object`.entity.channel.VoiceChannel
import discord4j.core.spec.VoiceChannelJoinSpec
import discord4j.voice.VoiceConnection
import kotlinx.coroutines.reactor.awaitSingle
import org.koin.java.KoinJavaComponent.inject
import kotlin.jvm.optionals.getOrNull

private val loggerFactory: LoggerFactory by inject(LoggerFactory::class.java)
private val logger = loggerFactory.getLogger("com.usadapekora.bot.discordbot.voice.VoiceChannelExtensionKt")
private val voiceChannelConnections: MutableMap<String, VoiceChannelJoinState> = mutableMapOf()

class VoiceChannelJoinState(
    val playerManager: AudioPlayerManager,
    val scheduler: AudioTrackScheduler,
    val voiceConnection: VoiceConnection
)

suspend fun joinVoiceChannel(voiceChannel: VoiceChannel): VoiceChannelJoinState {
    val voiceChannelId = voiceChannel.id.asString()
    val state: VoiceChannelJoinState? = voiceChannelConnections[voiceChannelId]

    if (state != null && state.voiceConnection.isConnected.awaitSingle()) {
        return state
    }

    val playerManager = getPlayerManagerInstance()
    val player = playerManager.createPlayer()
    val audioProvider = getAudioProviderInstance(player)
    val scheduler = AudioTrackScheduler(player)

    val spec = VoiceChannelJoinSpec.builder()
        .provider(audioProvider)
        .build()

    val voiceConnection = voiceChannel.join(spec)
        .doOnError { logger.warning("Error joining voice channel: ${it.message}") }
        .awaitSingle()

    val newJoinState = VoiceChannelJoinState(
        playerManager = playerManager,
        scheduler = scheduler,
        voiceConnection = voiceConnection
    )

    voiceChannelConnections[voiceChannelId] = newJoinState
    return newJoinState
}

/**
 * Plays audio on voice channel
 *
 * @param source The path or url of the audio source
 */
suspend fun MessageCreateEvent.playAudioOnSenderVoiceChannel(source: String) {
    val member = member.getOrNull() ?: return logger.warning("The member is not available")

    val voiceState = member.voiceState
        .doOnError { logger.warning("The voice state of user ${member.nickname} is not available") }
        .awaitSingle()

    val voiceChannel = voiceState.channel
        .doOnError { logger.warning("The user ${member.nickname} is not in voice channel or It is not available") }
        .awaitSingle()

    val state = joinVoiceChannel(voiceChannel)
    state.playerManager.loadItem(source, state.scheduler)
}
