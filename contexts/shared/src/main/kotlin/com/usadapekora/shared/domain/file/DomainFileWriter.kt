package com.usadapekora.shared.domain.file

import arrow.core.Either

interface DomainFileWriter {
    fun write(content: ByteArray, destination: String): Either<DomainFileException, Unit>
}
