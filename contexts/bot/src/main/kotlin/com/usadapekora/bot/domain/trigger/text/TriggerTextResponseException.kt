package com.usadapekora.bot.domain.trigger.text

sealed class TriggerTextResponseException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : TriggerTextResponseException(message)
}
