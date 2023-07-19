package com.usadapekora.bot.infraestructure.trigger.persistence.json

import com.usadapekora.bot.domain.trigger.BuiltInTriggerRepository
import com.usadapekora.bot.domain.trigger.Trigger

class JsonResourceBuiltInTriggerRepository : BuiltInTriggerRepository {
    override fun findAll(): Array<Trigger> {
        TODO("Not yet implemented")
    }
}
