package com.usadapekora.shared.infrastructure.file.filesystem

import arrow.core.Either
import arrow.core.right
import com.usadapekora.shared.domain.file.DomainFileException
import com.usadapekora.shared.domain.file.DomainFileWriter
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

class FileSystemDomainFileWriter : DomainFileWriter {
    override fun write(content: ByteArray, destination: String): Either<DomainFileException, Unit> {
        Path(destination).parent.createDirectories()
        return File(destination).writeBytes(content).right()
    }
}
