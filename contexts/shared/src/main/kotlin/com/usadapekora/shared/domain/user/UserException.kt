package com.usadapekora.shared.domain.user

sealed class UserException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : UserException(message)
    class FailedToCreate(override val message: String? = null) : UserException(message)
    class AlreadyExists(override val message: String? = null) : UserException(message)
    class InvalidProvider(override val message: String? = null) : UserException(message)
}
