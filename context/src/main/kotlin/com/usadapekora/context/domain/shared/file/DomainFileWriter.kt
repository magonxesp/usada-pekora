package com.usadapekora.context.domain.shared.file

interface DomainFileWriter {
    fun write(content: ByteArray, destination: String)
}
