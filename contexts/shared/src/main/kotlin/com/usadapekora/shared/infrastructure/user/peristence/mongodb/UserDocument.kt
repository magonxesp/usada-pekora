package com.usadapekora.shared.infrastructure.user.peristence.mongodb

import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class UserDocument(
    var _id: ObjectId? = null,
    var id: String = "",
    var name: String = "",
    var avatar: String? = null,
    var discordId: String = "",
) : MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<User, UserDocument>(UserDocument()) {
        override fun fromEntity(entity: User, document: UserDocument): UserDocument = document.apply {
            _id = document._id
            id = entity.id.value
            name = entity.name.value
            avatar = entity.avatar?.value
            discordId = entity.discordId.value
        }
    }

    fun toEntity() = User.fromPrimitives(
        id = id,
        name = name,
        avatar = avatar,
        discordId = discordId
    )
}
