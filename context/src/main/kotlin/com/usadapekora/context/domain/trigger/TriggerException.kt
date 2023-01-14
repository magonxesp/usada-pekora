package com.usadapekora.context.domain.trigger

sealed class TriggerException : Exception() {
    class InvalidValue : TriggerException()
    class NotFound : TriggerException()
}
