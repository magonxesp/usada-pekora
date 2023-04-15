package com.usadapekora.bot.domain.trigger.text

enum class TriggerTextResponseContentType(val value: String) {
    TEXT("text"),
    GIF("gif"),
    IMAGE("image");

    companion object {
        fun fromValue(value: String): TriggerTextResponseContentType
            = values().first { it.value == value }
    }
}
