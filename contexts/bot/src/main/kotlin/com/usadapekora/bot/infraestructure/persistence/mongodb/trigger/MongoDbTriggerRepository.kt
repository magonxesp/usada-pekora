package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.*
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbRepository
import com.usadapekora.bot.domain.trigger.TriggerException
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbTriggerRepository : MongoDbRepository<Trigger, TriggerDocument>(
    collection = "triggers",
    documentIdProp = TriggerDocument::id,
    documentCompanion = TriggerDocument.Companion
), TriggerRepository {

    private fun <T> nullOnException(block: () -> T): T? = try {
        block()
    } catch(_: Exception) {
        null
    }

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

}
