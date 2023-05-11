package com.usadapekora.bot.domain.shared.file

import arrow.core.Either

interface DomainFileWriter {
    fun write(content: ByteArray, destination: String): Either<DomainFileError, Unit>
}
