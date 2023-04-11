package com.usadapekora.bot.domain.trigger.audio

interface TriggerAudioResponse {
    fun id(): String
    fun path(): String
    fun provider(): TriggerAudioResponseProvider
}
