package com.usadapekora.bot.domain.shared.file

import arrow.core.Either

interface DomainFileReader {
    fun read(source: String): Either<DomainFileError, ByteArray>
}
