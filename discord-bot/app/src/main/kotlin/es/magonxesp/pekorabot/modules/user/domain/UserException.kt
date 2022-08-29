package es.magonxesp.pekorabot.modules.user.domain

sealed class UserException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : UserException(message)
}
