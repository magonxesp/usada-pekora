package com.usadapekora.bot.application.trigger.delete.text

import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository

class TriggerTextResponseDeleter(private val repository: TriggerTextResponseRepository) {

    fun delete(id: String) {
        val textResponse = repository.find(TriggerTextResponseId(id))
        repository.delete(textResponse)
    }

}
