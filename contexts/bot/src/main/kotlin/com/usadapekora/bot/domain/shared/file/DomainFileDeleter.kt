package com.usadapekora.bot.domain.shared.file

import arrow.core.Either

interface DomainFileDeleter {
    fun delete(destination: String): Either<DomainFileError, Unit>
}
