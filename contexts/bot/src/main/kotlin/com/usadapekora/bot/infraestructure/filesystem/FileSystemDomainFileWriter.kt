package com.usadapekora.bot.infraestructure.filesystem

import arrow.core.Either
import arrow.core.right
import com.usadapekora.shared.domain.file.DomainFileError
import com.usadapekora.shared.domain.file.DomainFileWriter
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.writeBytes

class FileSystemDomainFileWriter : DomainFileWriter {
    override fun write(content: ByteArray, destination: String): Either<DomainFileError, Unit> {
        Path(destination).parent.createDirectories()
        return File(destination).writeBytes(content).right()
    }
}
