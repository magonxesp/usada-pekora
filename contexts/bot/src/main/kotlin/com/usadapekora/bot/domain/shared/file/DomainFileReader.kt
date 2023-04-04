package com.usadapekora.bot.domain.shared.file

interface DomainFileReader {
    fun read(source: String): ByteArray
}
