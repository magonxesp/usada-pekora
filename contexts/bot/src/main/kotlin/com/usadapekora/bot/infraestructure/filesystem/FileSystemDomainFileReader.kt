package com.usadapekora.bot.infraestructure.filesystem

import arrow.core.Either
import arrow.core.right
import com.usadapekora.bot.domain.shared.file.DomainFileError
import com.usadapekora.bot.domain.shared.file.DomainFileReader
import java.io.File

class FileSystemDomainFileReader : DomainFileReader {

    override fun read(source: String): Either<DomainFileError, ByteArray>
        = File(source).readBytes().right()

}
