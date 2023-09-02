package com.usadapekora.bot.discordbot.voice

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import discord4j.voice.AudioProvider

fun getPlayerManagerInstance(): AudioPlayerManager {
    val playerManager = DefaultAudioPlayerManager()
    AudioSourceManagers.registerRemoteSources(playerManager)
    AudioSourceManagers.registerLocalSource(playerManager)

    return playerManager
}

fun getAudioProviderInstance(player: AudioPlayer): AudioProvider {
    return LavaPlayerAudioProvider(player)
}
