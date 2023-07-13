package com.usadapekora.shared.domain.user

sealed class UserSessionError(open val message: String? = null) {
    class SaveError(override val message: String? = null) : UserSessionError(message = message)
    class NotFound(override val message: String? = null) : UserSessionError(message = message)
    class AlreadyExists(override val message: String? = null) : UserSessionError(message = message)
}
