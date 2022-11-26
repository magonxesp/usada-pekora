package com.usadapekora.context.shared.domain.file


interface UploadedFile {
    fun id(): String
    fun name(): String
    fun path(): String
    fun url(): String
    fun mimeType(): String
}
