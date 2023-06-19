package com.usadapekora.shared.infrastructure.bus.event.persistence.redis

import arrow.core.Either
import arrow.core.left
import com.usadapekora.shared.domain.bus.event.EventConsumed
import com.usadapekora.shared.domain.bus.event.EventConsumedError
import com.usadapekora.shared.domain.bus.event.EventConsumedRepository
import com.usadapekora.shared.infrastructure.persistence.redis.RedisRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import redis.clients.jedis.params.SetParams

class RedisEventConsumedRepository : RedisRepository(), EventConsumedRepository {

    private val json = Json

    private fun key(id: EventConsumed.EventConsumedId, consumedBy: EventConsumed.EventConsumedBy)
        = "event_consumed_${id.value}_consumed_by_${consumedBy.value}"

    override fun find(
        id: EventConsumed.EventConsumedId,
        consumedBy: EventConsumed.EventConsumedBy
    ): Either<EventConsumedError.NotFound, EventConsumed> = Either.catch {
        val jsonString = redisConnection { connection ->
            connection.get(key(id, consumedBy))
        }

        if (jsonString.isNullOrBlank()) {
            return EventConsumedError.NotFound("EventConsumed by id ${id.value} and consumed by ${consumedBy.value} not found").left()
        }

        json.decodeFromString<EventConsumedJson>(jsonString).toEntity()
    }.mapLeft { EventConsumedError.NotFound(it.message) }

    override fun save(entity: EventConsumed): Either<EventConsumedError.SaveError, Unit> = Either.catch {
        redisConnection { connection ->
            val jsonObject = EventConsumedJson.fromEntity(entity)
            val jsonString = json.encodeToString(jsonObject)
            connection.set(key(entity.id, entity.consumedBy), jsonString, SetParams().ex(30 * 60))
        }
        Unit
    }.mapLeft { EventConsumedError.SaveError(it.message) }
}
