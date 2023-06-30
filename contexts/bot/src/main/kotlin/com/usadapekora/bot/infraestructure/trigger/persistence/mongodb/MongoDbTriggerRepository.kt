package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerRepository
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbTriggerRepository : MongoDbRepository<Trigger>(
    collection = "triggers",
    documentIdProp = TriggerDocument::id,
), TriggerRepository {

    override fun all(): Array<Trigger> {
        val triggers = collectionQuery<TriggerDocument>("triggers") { collection ->
            collection.find()
        }

        return triggers.map { it.toEntity() }.toList().toTypedArray()
    }

    override fun find(id: Trigger.TriggerId): Either<TriggerException.NotFound, Trigger> {
        val trigger = oneQuery<TriggerDocument>("triggers") { collection ->
            collection.findOne(TriggerDocument::id eq id.value)
        }

        if (trigger != null) {
            return trigger.toEntity().right()
        }

        return TriggerException.NotFound().left()
    }

    override fun findByDiscordServer(id: Trigger.TriggerDiscordGuildId): Array<Trigger> {
        val triggers = collectionQuery<TriggerDocument>("triggers") { collection ->
            collection.find(TriggerDocument::discordGuildId eq id.value)
        }

        return triggers.map { it.toEntity() }.toList().toTypedArray()
    }

    override fun save(entity: Trigger) {
        performSave<TriggerDocument>(entity, TriggerDocument.Companion)
    }

    override fun delete(entity: Trigger) {
        performDelete(entity)
    }
}
