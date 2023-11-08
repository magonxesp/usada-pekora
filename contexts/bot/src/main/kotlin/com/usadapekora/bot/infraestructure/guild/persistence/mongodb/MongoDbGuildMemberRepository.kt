package com.usadapekora.bot.infraestructure.guild.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.GuildMember
import com.usadapekora.bot.domain.guild.GuildMemberException
import com.usadapekora.bot.domain.guild.GuildMemberRepository
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

class MongoDbGuildMemberRepository : MongoDbRepository<GuildMember>(
    collection = "guildMember",
    documentIdProp = GuildMemberDocument::userId,
), GuildMemberRepository {

    override fun find(user: User.UserId, guild: Guild.GuildId): Either<GuildMemberException.NotFound, GuildMember> {
        val member = oneQuery<GuildMemberDocument>(collection) { collection ->
            collection.findOne(and(GuildMemberDocument::userId eq user.value, GuildMemberDocument::guildId eq guild.value))
        } ?: return GuildMemberException.NotFound("guild member with user ${user.value} and guild ${guild.value} not found").left()

        return member.toEntity().right()
    }

    override fun findByUser(user: User.UserId): Array<GuildMember> {
        val members = collectionQuery<GuildMemberDocument>(collection) { collection ->
            collection.find(GuildMemberDocument::userId eq user.value)
        }

        return members.map { it.toEntity() }.toList().toTypedArray()
    }

    override fun save(entity: GuildMember): Either<GuildMemberException.SaveError, Unit> = Either.catch {
        writeQuery<GuildMemberDocument>(collection) { collection ->
            collection.updateOne(
                and(
                    GuildMemberDocument::userId eq entity.user.value,
                    GuildMemberDocument::guildId eq entity.guild.value
                ), GuildMemberDocument.fromEntity(entity)
            )
                .takeUnless { updateResult -> updateResult.modifiedCount > 0L }
                ?.apply { collection.insertOne(GuildMemberDocument.fromEntity(entity)) }
        }
    }.mapLeft { GuildMemberException.SaveError(it.message) }

    override fun delete(entity: GuildMember): Either<GuildMemberException.DeleteError, Unit> = Either.catch {
        writeQuery<GuildMemberDocument>(collection) { collection ->
            collection.deleteOne(
                and(
                    GuildMemberDocument::userId eq entity.user.value,
                    GuildMemberDocument::guildId eq entity.guild.value
                )
            )
        }
    }.mapLeft { GuildMemberException.DeleteError(it.message) }
}
