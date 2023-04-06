package com.usadapekora.bot.domain.trigger.exception

sealed class TriggerTextResponseException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : TriggerTextResponseException(message)
}
