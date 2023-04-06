package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.domain.trigger.*
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbRepository
import com.usadapekora.bot.domain.trigger.exception.TriggerException
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbTriggerRepository(
    private val audioRepository: MongoDbTriggerAudioRepository,
    private val textRepository: MongoDbTriggerTextRepository
) : MongoDbRepository<Trigger, TriggerDocument>(
    collection = "triggers",
    documentIdProp = TriggerDocument::id,
    documentCompanion = TriggerDocument.Companion
), TriggerRepository {

    private fun <T> nullOnException(block: () -> T): T? = try {
        block()
    } catch(_: Exception) {
        null
    }

    private fun toEntityWithRelations(document: TriggerDocument): Trigger {
        val entity = document.toEntity()

        entity.responseText = nullOnException {
            textRepository.find(TriggerTextResponseId(document.responseTextId ?: ""))
        }

        entity.responseAudio = nullOnException {
            audioRepository.find(
                id = TriggerAudioResponseId(document.responseAudioId ?: ""),
                provider = TriggerAudioProvider.fromValue(document.responseAudioProvider ?: "")
            )
        }

        return entity
    }

    override fun all(): Array<Trigger> {
        val triggers = collectionQuery<TriggerDocument>("triggers") { collection ->
            collection.find()
        }

        return triggers.map { toEntityWithRelations(it) }.toList().toTypedArray()
    }

    override fun find(id: Trigger.TriggerId): Trigger {
        val trigger = oneQuery<TriggerDocument>("triggers") { collection ->
            collection.findOne(TriggerDocument::id eq id.value)
        }

        if (trigger != null) {
            return toEntityWithRelations(trigger)
        }

        throw TriggerException.NotFound()
    }

    override fun findByDiscordServer(id: Trigger.TriggerDiscordGuildId): Array<Trigger> {
        val triggers = collectionQuery<TriggerDocument>("triggers") { collection ->
            collection.find(TriggerDocument::discordGuildId eq id.value)
        }

        return triggers.map { toEntityWithRelations(it) }.toList().toTypedArray()
    }

}
