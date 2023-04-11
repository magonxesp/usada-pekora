package com.usadapekora.bot.domain.trigger.text

enum class TriggerContentType(val value: String) {
    TEXT("text"),
    GIF("gif"),
    IMAGE("image");

    companion object {
        fun fromValue(value: String): TriggerContentType
            = values().first { it.value == value }
    }
}
