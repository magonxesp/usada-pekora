package com.usadapekora.bot.domain.trigger

enum class TriggerAudioProvider(val value: String) {
    DEFAULT("default");

    companion object {
        fun fromValue(value: String): TriggerAudioProvider
            = values().first { it.value == value }
    }
}
