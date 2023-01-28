package com.usadapekora.context.domain.shared.file

interface DomainFileReader {
    fun read(source: String): ByteArray
}
