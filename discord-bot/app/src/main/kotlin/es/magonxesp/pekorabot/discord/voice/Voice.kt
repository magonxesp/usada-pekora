package es.magonxesp.pekorabot.discord.voice

import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent
import com.sedmelluq.discord.lavaplayer.player.event.TrackEndEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.`object`.entity.channel.VoiceChannel
import discord4j.core.spec.VoiceChannelJoinSpec
import es.magonxesp.pekorabot.discord.audio.AudioScheduler
import es.magonxesp.pekorabot.discord.audio.audioProviderInstance
import es.magonxesp.pekorabot.discord.audio.playerManagerInstance
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking

private val playerManager = playerManagerInstance()
private val player = playerManager.createPlayer()
private val audioProvider = audioProviderInstance(player)
private val audioScheduler = AudioScheduler(player)
private lateinit var voiceChannel: VoiceChannel
private lateinit var trackEndListener: (event: AudioEvent) -> Unit

suspend fun MessageCreateEvent.joinVoiceChannel() {
    val voiceState = member.orElse(null)?.voiceState?.awaitSingle() ?: return
    voiceChannel = voiceState.channel.awaitSingle()
    val spec = VoiceChannelJoinSpec.builder().apply {
        provider(audioProvider)
    }.build()

    voiceChannel.join(spec).awaitSingle()
}

/**
 * Play an audio file to the joined voice channel
 *
 * @param reference The url or path of an audio file
 */
fun playAudio(reference: String) {
    playerManager.loadItem(reference, audioScheduler)
    trackEndListener = {
        runBlocking {
            if (it is TrackEndEvent && it.track.identifier == reference) {
                voiceChannel.sendDisconnectVoiceState().awaitSingle()
            }
        }

        player.removeListener(trackEndListener)
    }

    player.addListener(trackEndListener)
}
