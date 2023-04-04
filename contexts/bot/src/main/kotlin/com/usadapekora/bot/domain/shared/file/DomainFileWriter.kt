package com.usadapekora.bot.domain.shared.file

interface DomainFileWriter {
    fun write(content: ByteArray, destination: String)
}
