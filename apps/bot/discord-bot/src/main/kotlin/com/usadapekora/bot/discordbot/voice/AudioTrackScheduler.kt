package com.usadapekora.bot.discordbot.voice

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack

class AudioTrackScheduler(private val audioPlayer: AudioPlayer) : AudioLoadResultHandler {
    override fun trackLoaded(track: AudioTrack?) {
        audioPlayer.playTrack(track)
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {
        //TODO("Not yet implemented")
    }

    override fun noMatches() {
        //TODO("Not yet implemented")
    }

    override fun loadFailed(exception: FriendlyException?) {
        //TODO("Not yet implemented")
    }
}
