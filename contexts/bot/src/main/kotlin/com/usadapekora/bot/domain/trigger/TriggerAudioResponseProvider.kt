package com.usadapekora.bot.domain.trigger

enum class TriggerAudioResponseProvider(val value: String) {
    DEFAULT("default");

    companion object {
        fun fromValue(value: String): TriggerAudioResponseProvider
            = values().first { it.value == value }
    }
}
