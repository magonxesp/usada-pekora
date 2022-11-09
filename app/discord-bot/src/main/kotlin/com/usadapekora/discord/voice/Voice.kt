package com.usadapekora.discord.voice

import discord4j.core.event.domain.message.MessageCreateEvent
import com.usadapekora.discord.audio.AudioScheduler
import com.usadapekora.discord.audio.audioProviderInstance
import com.usadapekora.discord.audio.playerManagerInstance

private val playerManager = playerManagerInstance()
private val player = playerManager.createPlayer()
private val audioProvider = audioProviderInstance(player)
private val audioScheduler = AudioScheduler(player)

/**
 * Play an audio file to the joined voice channel
 *
 * @param reference The url or path of an audio file
 */
suspend fun MessageCreateEvent.playAudio(reference: String) {
    val channelPlayer = VoiceChannelPlayer(
        playerManager,
        audioScheduler,
        audioProvider,
        player
    )

    channelPlayer.join(member.get())
    channelPlayer.play(reference)
}
