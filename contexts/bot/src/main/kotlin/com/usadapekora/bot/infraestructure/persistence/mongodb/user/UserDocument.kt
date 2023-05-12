package com.usadapekora.bot.infraestructure.persistence.mongodb.user

import com.usadapekora.bot.domain.user.User
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class UserDocument(
    var _id: ObjectId? = null,
    var id: String = "",
    var discordId: String = "",
) : MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<User, UserDocument>(UserDocument()) {
        override fun fromEntity(entity: User, document: UserDocument): UserDocument = document.apply {
            _id = document._id
            id = entity.id.value
            discordId = entity.discordId.value
        }
    }

    fun toEntity() = User.fromPrimitives(
        id = id,
        discordId = discordId
    )
}
