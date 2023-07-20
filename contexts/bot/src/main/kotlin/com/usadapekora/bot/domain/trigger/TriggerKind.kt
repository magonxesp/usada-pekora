package com.usadapekora.bot.domain.trigger

enum class TriggerKind(val value: String) {
    BUILT_IN("built_in"),
    PRIVATE("private");

    companion object {
        fun fromValue(value: String) =
            values().firstOrNull { it.value == value }
            ?: throw TriggerException.InvalidKind("The trigger kind $value is invalid")
    }
}
