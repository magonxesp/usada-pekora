package com.usadapekora.backend.utils

import com.usadapekora.context.domain.shared.file.MimeType
import com.usadapekora.context.infraestructure.metadata.FileMetadataUtils
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest
import kotlin.random.Random

object MultipartFileUtils {
    fun makeTemporaryFileName(file: MultipartFile): String {
        val fileMimeType = FileMetadataUtils.fileMimeTypeFromByteArray(file.bytes)
        val extension = MimeType.fromString(fileMimeType).extensions.last()
        val fileName = MessageDigest.getInstance("MD5").digest(Random.Default.nextBytes(10)).toString()

        return "$fileName$extension"
    }
}
