package com.usadapekora.bot.domain.trigger

interface TriggerAudioResponse {
    fun id(): String
    fun path(): String
    fun provider(): TriggerAudioProvider
}
