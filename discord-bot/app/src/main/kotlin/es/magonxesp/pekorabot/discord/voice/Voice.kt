package es.magonxesp.pekorabot.discord.voice

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.VoiceChannelJoinSpec
import discord4j.voice.AudioProvider
import kotlinx.coroutines.reactor.awaitSingle


fun playerManager(): AudioPlayerManager {
    val playerManager = DefaultAudioPlayerManager()

    playerManager.configuration.setFrameBufferFactory(::NonAllocatingAudioFrameBuffer)
    AudioSourceManagers.registerRemoteSources(playerManager)
    AudioSourceManagers.registerLocalSource(playerManager)

    return playerManager
}

fun audioProviderInstance(): AudioProvider {
    val player = playerManager().createPlayer()
    return LavaPlayerAudioProvider(player)
}


suspend fun MessageCreateEvent.joinVoiceChannel() {
    val voiceState = member.orElse(null)?.voiceState?.awaitSingle() ?: return

    val channel = voiceState.channel.awaitSingle()
    val spec = VoiceChannelJoinSpec.builder().apply {
        provider(audioProviderInstance())
    }.build()

    channel.join(spec).awaitSingle()

    TODO("Get the audio url and play on the joined channel")
}
