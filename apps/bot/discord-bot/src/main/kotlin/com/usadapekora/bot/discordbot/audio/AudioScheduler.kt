package com.usadapekora.bot.discordbot.audio

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack

class AudioScheduler(val player: AudioPlayer) : AudioLoadResultHandler {

    override fun trackLoaded(track: AudioTrack?) {
        if (track != null) {
            player.playTrack(track)
        }
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {

    }

    override fun noMatches() {

    }

    override fun loadFailed(exception: FriendlyException?) {

    }

}
