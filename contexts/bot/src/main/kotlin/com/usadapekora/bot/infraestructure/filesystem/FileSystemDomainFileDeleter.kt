package com.usadapekora.bot.infraestructure.filesystem

import com.usadapekora.bot.domain.shared.file.DomainFileDeleter
import java.io.File

class FileSystemDomainFileDeleter : DomainFileDeleter {

    override fun delete(destination: String) {
        val file = File(destination)

        if (file.exists()) {
            file.delete()
        }
    }

}
