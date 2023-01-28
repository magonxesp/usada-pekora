package com.usadapekora.context.infraestructure.filesystem

import com.usadapekora.context.domain.shared.file.DomainFileDeleter
import java.io.File

class FileSystemDomainFileDeleter : DomainFileDeleter {

    override fun delete(destination: String) {
        val file = File(destination)

        if (file.exists()) {
            file.delete()
        }
    }

}
