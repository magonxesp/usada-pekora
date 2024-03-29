package com.usadapekora.shared.infrastructure.user.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.domain.user.UserSession
import com.usadapekora.shared.domain.user.UserSessionException
import com.usadapekora.shared.domain.user.UserSessionRepository
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbUserSessionRepository : MongoDbRepository<UserSession>(
    collection = "userSession",
    documentIdProp = UserSessionDocument::id
), UserSessionRepository {
    override fun find(id: UserSession.UserSessionId): Either<UserSessionException.NotFound, UserSession> {
        val document = oneQuery<UserSessionDocument>(collection) { collection ->
            collection.findOne(UserSessionDocument::id eq id.value)
        } ?: return UserSessionException.NotFound("User session with id ${id.value} not found").left()
        return document.toEntity().right()
    }

    override fun save(entity: UserSession): Either<UserSessionException.SaveError, Unit> =
        Either.catch { performSave<UserSessionDocument>(entity, UserSessionDocument) }
            .mapLeft { UserSessionException.SaveError(it.message) }
}
