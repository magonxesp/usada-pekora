package es.magonxesp.pekorabot.modules.trigger.domain

interface TriggerRepository {
    fun all(): Array<Trigger>
    fun findByDiscordServer(id: String): Array<Trigger>
    fun save(trigger: Trigger)
}
