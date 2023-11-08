package com.usadapekora.bot.infraestructure.guild.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.GuildException
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.domain.guild.GuildRepository
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.MongoOperator.lookup
import org.litote.kmongo.MongoOperator.match
import org.litote.kmongo.aggregate
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbGuildRepository : MongoDbRepository<Guild>(
    collection = "guild",
    documentIdProp = GuildDocument::id,
), GuildRepository {

    override fun find(id: Guild.GuildId): Either<GuildException.NotFound, Guild> {
        val guild = oneQuery<GuildDocument>(collection) { collection ->
            collection.findOne(GuildDocument::id eq id.value)
        } ?: return GuildException.NotFound("guild with id ${id.value} not found").left()

        return guild.toEntity().right()
    }

    override fun findByProvider(
        providerId: Guild.GuildProviderId,
        provider: GuildProvider
    ): Either<GuildException.NotFound, Guild> {
        val guild = oneQuery<GuildDocument>(collection) { collection ->
            collection.findOne(and(GuildDocument::providerId eq providerId.value, GuildDocument::provider eq provider.value))
        } ?: return GuildException.NotFound("guild with provider id ${providerId.value} of provider ${provider.value} not found")
            .left()

        return guild.toEntity().right()
    }

    override fun findByUserId(userId: User.UserId): Array<Guild> {
        return collectionQuery<GuildDocument>(collection) { collection ->
            collection.aggregate<GuildDocument>("""
                [
                    {
                        $lookup:
                            {
                                from: "guildMember",
                                localField: "id",
                                foreignField: "guildId",
                                as: "members",
                            },
                    },
                    {
                        $match:
                            {
                                "members.userId": "${userId.value}",
                            },
                    },
                ]
            """)
        }
            .asIterable()
            .map { it.toEntity() }
            .toTypedArray()
    }

    override fun save(entity: Guild): Either<GuildException.SaveError, Unit> = Either.catch {
        performSave<GuildDocument>(entity, GuildDocument.Companion)
    }.mapLeft { GuildException.SaveError(it.message) }

    override fun delete(entity: Guild): Either<GuildException.DeleteError, Unit> = Either.catch {
       performDelete(entity)
    }.mapLeft { GuildException.DeleteError(it.message) }
}
