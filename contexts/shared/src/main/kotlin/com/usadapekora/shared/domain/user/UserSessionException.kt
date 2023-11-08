package com.usadapekora.shared.domain.user

sealed class UserSessionException(override val message: String? = null) : Exception(message) {
    class SaveError(override val message: String? = null) : UserSessionException(message = message)
    class NotFound(override val message: String? = null) : UserSessionException(message = message)
    class AlreadyExists(override val message: String? = null) : UserSessionException(message = message)
}
