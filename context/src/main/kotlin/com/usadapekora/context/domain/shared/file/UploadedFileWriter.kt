package com.usadapekora.context.domain.shared.file

import java.io.File

interface UploadedFileWriter {
    fun write(content: ByteArray, destinationFilePath: String)
}
