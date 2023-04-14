package com.usadapekora.bot.application.trigger.find.text

import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository

class TriggerTextResponseFinder(private val repository: TriggerTextResponseRepository) {

    fun find(id: String): TriggerTextResponseFindResponse {
        val textResponse = repository.find(TriggerTextResponseId(id))

        return TriggerTextResponseFindResponse(
            id = textResponse.id.value,
            content = textResponse.content.value,
            type = textResponse.type.value
        )
    }

}
