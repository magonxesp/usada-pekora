package es.magonxesp.pekorabot.modules.trigger.domain

interface TriggerRepository {
    fun all(): Array<Trigger>
}
