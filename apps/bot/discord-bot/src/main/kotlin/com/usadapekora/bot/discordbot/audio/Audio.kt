package com.usadapekora.bot.discordbot.audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer
import discord4j.voice.AudioProvider


private val playerManager: AudioPlayerManager = DefaultAudioPlayerManager()

fun playerManagerInstance(): AudioPlayerManager {
    playerManager.apply {
        configuration.setFrameBufferFactory(::NonAllocatingAudioFrameBuffer)
        AudioSourceManagers.registerRemoteSources(this)
        AudioSourceManagers.registerLocalSource(this)
    }

    return playerManager
}

fun audioProviderInstance(player: AudioPlayer): AudioProvider {
    return LavaPlayerAudioProvider(player)
}
