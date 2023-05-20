package com.usadapekora.shared.domain.file

import arrow.core.Either

interface DomainFileReader {
    fun read(source: String): Either<DomainFileError, ByteArray>
}
