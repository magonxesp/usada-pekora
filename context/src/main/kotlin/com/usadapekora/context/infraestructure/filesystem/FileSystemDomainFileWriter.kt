package com.usadapekora.context.infraestructure.filesystem

import com.usadapekora.context.domain.shared.file.DomainFileWriter
import java.io.File
import kotlin.io.writeBytes

class FileSystemDomainFileWriter : DomainFileWriter {
    override fun write(content: ByteArray, destination: String) {
        File(destination).writeBytes(content)
    }
}
