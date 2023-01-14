package com.usadapekora.context.domain.user

import com.usadapekora.context.domain.shared.Entity

data class User(val id: String, val discordId: String) : Entity() {
    override fun id(): String = id
}
