package com.usadapekora.bot.backend.utils

import com.usadapekora.shared.domain.file.MimeType
import com.usadapekora.bot.infraestructure.metadata.FileMetadataUtils
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
