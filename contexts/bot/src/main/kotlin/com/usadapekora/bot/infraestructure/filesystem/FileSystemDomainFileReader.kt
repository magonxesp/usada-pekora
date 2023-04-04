package com.usadapekora.bot.infraestructure.filesystem

import com.usadapekora.bot.domain.shared.file.DomainFileReader
import java.io.File

class FileSystemDomainFileReader : DomainFileReader {

    override fun read(source: String): ByteArray
        = File(source).readBytes()

}
