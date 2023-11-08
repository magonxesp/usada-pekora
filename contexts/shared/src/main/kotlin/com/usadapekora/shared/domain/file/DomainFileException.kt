package com.usadapekora.shared.domain.file

sealed class DomainFileException(override val message: String? = null) : Exception(message)
