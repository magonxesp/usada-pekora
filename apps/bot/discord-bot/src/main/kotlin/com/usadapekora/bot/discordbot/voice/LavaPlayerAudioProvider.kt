package com.usadapekora.bot.discordbot.voice

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame
import discord4j.voice.AudioProvider
import java.nio.ByteBuffer

class LavaPlayerAudioProvider(
    val audioPlayer: AudioPlayer
) : AudioProvider(
    ByteBuffer.allocate(StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize())
) {
    private val frame = MutableAudioFrame()

    init {
        frame.setBuffer(buffer)
    }

    override fun provide(): Boolean {
        val didProvide = audioPlayer.provide(frame)

        if (didProvide) {
            buffer.flip()
        }

        return didProvide
    }
}
