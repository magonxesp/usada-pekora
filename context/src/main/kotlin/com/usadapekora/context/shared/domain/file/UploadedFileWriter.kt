package com.usadapekora.context.shared.domain.file

import java.io.File

interface UploadedFileWriter {
    fun write(file: File, destinationPath: String)
}
