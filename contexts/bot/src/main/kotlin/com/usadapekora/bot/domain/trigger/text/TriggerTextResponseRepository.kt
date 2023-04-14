package com.usadapekora.bot.domain.trigger.text

interface TriggerTextResponseRepository {
    fun find(id: TriggerTextResponseId): TriggerTextResponse
    fun save(entity: TriggerTextResponse)
    fun delete(entity: TriggerTextResponse)
}
