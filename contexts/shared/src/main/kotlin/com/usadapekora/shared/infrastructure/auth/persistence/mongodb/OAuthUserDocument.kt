package com.usadapekora.shared.infrastructure.auth.persistence.mongodb

import com.usadapekora.shared.domain.auth.OAuthUser
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId
import java.util.*

class OAuthUserDocument(
    var _id: ObjectId? = null,
    var id: String = "",
    var name: String? = null,
    var avatar: String? = null,
    var token: String = "",
    var provider: String = "",
    var userId: String = UUID.randomUUID().toString()
) : MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<OAuthUser, OAuthUserDocument>({ OAuthUserDocument() }) {
        override fun fromEntity(entity: OAuthUser, document: OAuthUserDocument): OAuthUserDocument {
            document.id = entity.id
            document.name = entity.name
            document.avatar = entity.avatar
            document.token = entity.token
            document.provider = entity.provider
            document.userId = entity.userId

            return document
        }
    }

    fun toEntity() = OAuthUser(
        id = id,
        name = name,
        avatar = avatar,
        token = token,
        provider = provider,
        userId = userId
    )
}
