package com.usadapekora.bot.domain.user

import com.usadapekora.bot.domain.shared.Entity

data class User(val id: String, val discordId: String) : Entity() {
    override fun id(): String = id
}
