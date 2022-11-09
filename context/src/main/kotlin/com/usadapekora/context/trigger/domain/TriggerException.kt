package com.usadapekora.context.trigger.domain

sealed class TriggerException : Exception() {
    class InvalidValue : TriggerException()
    class NotFound : TriggerException()
}
