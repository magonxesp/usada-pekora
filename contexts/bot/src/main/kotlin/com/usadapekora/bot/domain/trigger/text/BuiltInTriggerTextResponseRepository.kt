package com.usadapekora.bot.domain.trigger.text

import arrow.core.Either

interface BuiltInTriggerTextResponseRepository {
    fun find(id: TriggerTextResponseId): Either<TriggerTextResponseException, TriggerTextResponse>
}
