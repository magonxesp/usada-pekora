package com.usadapekora.bot.domain.trigger.audio

interface TriggerAudioResponse {
    fun id(): String
    val path: String
    val provider: TriggerAudioResponseProvider
}
