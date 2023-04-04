package com.usadapekora.bot.discordbot.voice

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent
import com.sedmelluq.discord.lavaplayer.player.event.TrackEndEvent
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.channel.VoiceChannel
import discord4j.core.spec.VoiceChannelJoinSpec
import discord4j.voice.AudioProvider
import com.usadapekora.bot.discordbot.audio.AudioScheduler
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.runBlocking

class VoiceChannelPlayer(
    private val playerManager: AudioPlayerManager,
    private val scheduler: AudioScheduler,
    private val audioProvider: AudioProvider,
    private val player: AudioPlayer
) {
    private lateinit var voiceChannel: VoiceChannel
    private var joined = false

    private val trackEndListener: (event: AudioEvent) -> Unit = {
        runBlocking {
            if (it is TrackEndEvent) {
                disconnect()
            }
        }
    }

    private val spec = VoiceChannelJoinSpec.builder().apply {
        provider(audioProvider)
    }.build()

    private suspend fun disconnect() {
        voiceChannel.sendDisconnectVoiceState().awaitSingleOrNull()
        joined = false
    }

    suspend fun join(member: Member) {
        if (joined) return

        val voiceState = member.voiceState.awaitSingle()
        voiceChannel = voiceState.channel.awaitSingle()
        voiceChannel.join(spec).awaitSingle()
        joined = true
    }

    fun play(reference: String) {
        if (!joined) return

        playerManager.loadItem(reference, scheduler)
        player.removeListener(trackEndListener)
        player.addListener(trackEndListener)
    }
}
