package com.usadapekora.bot.infraestructure.guild.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.*
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

class MongoDbGuildRepository : MongoDbRepository<Guild, GuildDocument>(
    collection = "guild",
    documentIdProp = GuildDocument::id,
    documentCompanion = GuildDocument.Companion
), GuildRepository {

    override fun find(id: Guild.GuildId): Either<GuildError.NotFound, Guild> {
        val guild = oneQuery<GuildDocument>(collection) { collection ->
            collection.findOne(GuildDocument::id eq id.value)
        } ?: return GuildError.NotFound("guild with id ${id.value} not found").left()

        return guild.toEntity().right()
    }

    override fun findByProvider(
        providerId: Guild.GuildProviderId,
        provider: GuildProvider
    ): Either<GuildError.NotFound, Guild> {
        val guild = oneQuery<GuildDocument>(collection) { collection ->
            collection.findOne(and(GuildDocument::providerId eq providerId.value, GuildDocument::provider eq provider.value))
        } ?: return GuildError.NotFound("guild with provider id ${providerId.value} of provider ${provider.value} not found").left()

        return guild.toEntity().right()
    }

    override fun save(entity: Guild): Either<GuildError.SaveError, Unit> = Either.catch {
        performSave(entity)
    }.mapLeft { GuildError.SaveError(it.message) }

    override fun delete(entity: Guild): Either<GuildError.DeleteError, Unit> = Either.catch {
       performDelete(entity)
    }.mapLeft { GuildError.DeleteError(it.message) }
}
