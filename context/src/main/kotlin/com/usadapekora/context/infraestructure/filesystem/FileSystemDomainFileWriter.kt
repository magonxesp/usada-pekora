package com.usadapekora.context.infraestructure.filesystem

import com.usadapekora.context.domain.shared.file.DomainFileWriter
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.writeBytes

class FileSystemDomainFileWriter : DomainFileWriter {
    override fun write(content: ByteArray, destination: String) {
        Path(destination).parent.createDirectories()
        File(destination).writeBytes(content)
    }
}
