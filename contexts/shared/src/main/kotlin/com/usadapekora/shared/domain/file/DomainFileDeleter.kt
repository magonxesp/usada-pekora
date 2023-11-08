package com.usadapekora.shared.domain.file

import arrow.core.Either

interface DomainFileDeleter {
    fun delete(destination: String): Either<DomainFileException, Unit>
}
