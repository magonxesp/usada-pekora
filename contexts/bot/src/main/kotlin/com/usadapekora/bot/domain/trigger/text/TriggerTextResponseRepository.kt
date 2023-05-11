package com.usadapekora.bot.domain.trigger.text

import arrow.core.Either

interface TriggerTextResponseRepository {
    fun find(id: TriggerTextResponseId): Either<TriggerTextResponseException, TriggerTextResponse>
    fun save(entity: TriggerTextResponse)
    fun delete(entity: TriggerTextResponse)
}
