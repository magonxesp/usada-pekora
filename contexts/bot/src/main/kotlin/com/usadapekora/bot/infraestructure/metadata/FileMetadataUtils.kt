package com.usadapekora.bot.infraestructure.metadata

import org.apache.tika.io.TikaInputStream
import org.apache.tika.mime.MediaType
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import java.io.ByteArrayInputStream

object FileMetadataUtils {
    fun fileMimeTypeFromByteArray(bytes: ByteArray): String {
        val parser = AutoDetectParser()
        val metadata = Metadata()
        val stream: TikaInputStream = TikaInputStream.get(ByteArrayInputStream(bytes))
        val mediaType: MediaType = parser.detector.detect(stream, metadata)

        return mediaType.toString()
    }
}
