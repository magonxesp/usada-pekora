package com.usadapekora.shared.infrastructure.auth.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.domain.auth.OAuthUser
import com.usadapekora.shared.domain.auth.OAuthUserError
import com.usadapekora.shared.domain.auth.OAuthUserRepository
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbOAuthUserRepository : MongoDbRepository<OAuthUser>(
    documentIdProp = OAuthUserDocument::id,
    collection = "oAuthUser"
), OAuthUserRepository {
    override fun find(userId: User.UserId): Either<OAuthUserError.NotFound, OAuthUser> {
        val document = oneQuery<OAuthUserDocument>(collection) { collection ->
            collection.findOne(OAuthUserDocument::userId eq userId.value)
        } ?: return OAuthUserError.NotFound("OAuthUser with user id ${userId.value} not found").left()
        return document.toEntity().right()
    }

    override fun save(entity: OAuthUser): Either<OAuthUserError.SaveError, Unit> = Either.catch {
        performSave<OAuthUserDocument>(entity, OAuthUserDocument.Companion)
    }.mapLeft { OAuthUserError.SaveError(it.message) }
}
