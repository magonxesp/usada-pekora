package com.usadapekora.context.infraestructure.persistence.mongodb.trigger

import com.usadapekora.context.infraestructure.persistence.mongodb.MongoDbRepository
import com.usadapekora.context.domain.trigger.Trigger
import com.usadapekora.context.domain.trigger.TriggerException
import com.usadapekora.context.domain.trigger.TriggerRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbTriggerRepository : MongoDbRepository<Trigger, TriggerDocument>(
    collection = "triggers",
    documentIdProp = TriggerDocument::id,
    documentCompanion = TriggerDocument.Companion
), TriggerRepository {

    override fun all(): Array<Trigger> {
        val triggers = collectionQuery<TriggerDocument>("triggers") { collection ->
            collection.find()
        }

        return triggers.map { it.toEntity() }.toList().toTypedArray()
    }

    override fun find(id: Trigger.TriggerId): Trigger {
        val trigger = oneQuery<TriggerDocument>("triggers") { collection ->
            collection.findOne(TriggerDocument::id eq id.value)
        }

        if (trigger != null) {
            return trigger.toEntity()
        }

        throw TriggerException.NotFound()
    }

    override fun findByDiscordServer(id: Trigger.TriggerDiscordGuildId): Array<Trigger> {
        val triggers = collectionQuery<TriggerDocument>("triggers") { collection ->
            collection.find(TriggerDocument::discordGuildId eq id.value)
        }

        return triggers.map { it.toEntity() }.toList().toTypedArray()
    }

}