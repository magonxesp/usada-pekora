package com.usadapekora.context.infraestructure.filesystem

import com.usadapekora.context.domain.shared.file.DomainFileReader
import java.io.File

class FileSystemDomainFileReader : DomainFileReader {

    override fun read(source: String): ByteArray
        = File(source).readBytes()

}
