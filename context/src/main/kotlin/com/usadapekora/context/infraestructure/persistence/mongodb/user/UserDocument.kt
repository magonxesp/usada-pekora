package com.usadapekora.context.infraestructure.persistence.mongodb.user

import com.usadapekora.context.domain.user.User
import com.usadapekora.context.infraestructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.context.infraestructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class UserDocument(
    val _id: ObjectId? = null,
    var id: String = "",
    var discordId: String = "",
) : MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<User, UserDocument>(UserDocument()) {
        override fun fromEntity(entity: User, document: UserDocument): UserDocument = document.apply {
            id = entity.id
            discordId = entity.discordId
        }
    }

    fun toEntity() = User(
        id = id,
        discordId = discordId
    )
}
