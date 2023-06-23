package com.usadapekora.shared.domain.auth

import com.usadapekora.shared.domain.Entity
import java.util.*

data class OAuthUser(
    val id: String,
    val name: String? = null,
    val avatar: String? = null,
    val token: String,
    val provider: String,
    val userId: String = UUID.randomUUID().toString()
) : Entity() {
    override fun id() = id
}
