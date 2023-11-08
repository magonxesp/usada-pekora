package com.usadapekora.shared.infrastructure.file.filesystem

import arrow.core.Either
import arrow.core.right
import com.usadapekora.shared.domain.file.DomainFileDeleter
import com.usadapekora.shared.domain.file.DomainFileException
import java.io.File

class FileSystemDomainFileDeleter : DomainFileDeleter {

    override fun delete(destination: String): Either<DomainFileException, Unit> {
        val file = File(destination)

        if (file.exists()) {
            file.delete()
        }

        return Unit.right()
    }

}
