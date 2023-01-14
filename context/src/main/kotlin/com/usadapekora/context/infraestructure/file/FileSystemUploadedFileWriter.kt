package com.usadapekora.context.infraestructure.file

import com.usadapekora.context.domain.shared.file.UploadedFileWriter
import java.io.File
import kotlin.io.writeBytes

class FileSystemUploadedFileWriter : UploadedFileWriter {
    override fun write(content: ByteArray, destinationFilePath: String) {
        File(destinationFilePath).writeBytes(content)
    }
}
