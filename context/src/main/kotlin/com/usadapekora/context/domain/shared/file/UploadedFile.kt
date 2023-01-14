package com.usadapekora.context.domain.shared.file


interface UploadedFile {
    fun id(): String
    fun name(): String
    fun path(): String
    fun url(): String
    fun mimeType(): String
}
