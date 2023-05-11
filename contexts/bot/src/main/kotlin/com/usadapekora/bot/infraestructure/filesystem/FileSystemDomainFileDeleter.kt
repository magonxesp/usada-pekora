package com.usadapekora.bot.infraestructure.filesystem

import arrow.core.Either
import arrow.core.right
import com.usadapekora.bot.domain.shared.file.DomainFileDeleter
import com.usadapekora.bot.domain.shared.file.DomainFileError
import java.io.File

class FileSystemDomainFileDeleter : DomainFileDeleter {

    override fun delete(destination: String): Either<DomainFileError, Unit> {
        val file = File(destination)

        if (file.exists()) {
            file.delete()
        }

        return Unit.right()
    }

}
