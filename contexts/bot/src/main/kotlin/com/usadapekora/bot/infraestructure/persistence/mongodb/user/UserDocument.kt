package com.usadapekora.bot.infraestructure.persistence.mongodb.user

import com.usadapekora.bot.domain.user.User
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDomainEntityDocument
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
