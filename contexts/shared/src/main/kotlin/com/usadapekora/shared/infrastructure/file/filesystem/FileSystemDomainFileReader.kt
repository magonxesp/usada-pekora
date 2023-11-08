package com.usadapekora.shared.infrastructure.file.filesystem

import arrow.core.Either
import arrow.core.right
import com.usadapekora.shared.domain.file.DomainFileException
import com.usadapekora.shared.domain.file.DomainFileReader
import java.io.File

class FileSystemDomainFileReader : DomainFileReader {

    override fun read(source: String): Either<DomainFileException, ByteArray>
        = File(source).readBytes().right()

}
