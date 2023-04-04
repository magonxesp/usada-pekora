package com.usadapekora.bot.infraestructure.filesystem

import com.usadapekora.bot.domain.shared.file.DomainFileWriter
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
