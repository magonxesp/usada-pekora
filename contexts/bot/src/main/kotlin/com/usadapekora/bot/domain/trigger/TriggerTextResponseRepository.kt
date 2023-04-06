package com.usadapekora.bot.domain.trigger

interface TriggerTextResponseRepository {
    fun find(id: TriggerTextResponseId): TriggerTextResponse
}
