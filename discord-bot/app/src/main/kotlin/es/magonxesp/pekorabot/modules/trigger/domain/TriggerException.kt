package es.magonxesp.pekorabot.modules.trigger.domain

sealed class TriggerException : Exception() {
    class InvalidValue : TriggerException()
    class NotFound : TriggerException()
}
