package com.usadapekora.context.trigger.infraestructure.persistence.mongodb

import com.usadapekora.context.shared.infraestructure.persistence.MongoDbRepository
import com.usadapekora.context.trigger.domain.Trigger
import com.usadapekora.context.trigger.domain.TriggerException
import com.usadapekora.context.trigger.domain.TriggerRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

class MongoDbTriggerRepository : MongoDbRepository(), TriggerRepository {

    override fun all(): Array<Trigger> {
        val triggers = collectionQuery<TriggerDocument>("triggers") { collection ->
            collection.find()
        }

        return triggers.map { it.toAggregate() }.toList().toTypedArray()
    }

    override fun find(id: Trigger.TriggerId): Trigger {
        val trigger = oneQuery<TriggerDocument>("triggers") { collection ->
            collection.findOne(TriggerDocument::id eq id.value)
        }

        if (trigger != null) {
            return trigger.toAggregate()
        }

        throw TriggerException.NotFound()
    }

    override fun findByDiscordServer(id: Trigger.TriggerDiscordGuildId): Array<Trigger> {
        val triggers = collectionQuery<TriggerDocument>("triggers") { collection ->
            collection.find(TriggerDocument::discordGuildId eq id.value)
        }

        return triggers.map { it.toAggregate() }.toList().toTypedArray()
    }

    override fun save(trigger: Trigger) {
        writeQuery<TriggerDocument>("triggers") { collection ->
            val document = collection.findOne(TriggerDocument::id eq trigger.id.value)

            if (document != null) {
                collection.updateOne(TriggerDocument.fromAggregate(trigger, document))
            } else {
                collection.insertOne(TriggerDocument.fromAggregate(trigger))
            }
        }
    }

    override fun delete(trigger: Trigger) {
        writeQuery<TriggerDocument>("triggers") { collection ->
            collection.deleteOne(TriggerDocument::id eq trigger.id.value)
        }
    }
}
