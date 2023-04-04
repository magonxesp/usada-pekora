package com.usadapekora.bot.infraestructure.filesystem

import com.usadapekora.bot.domain.Random
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class FileSystemDomainFileWriterTest : FileSystemTest() {

    @Test
    fun `should write file contents to destination`() {
        val content = Random.instance().chiquito.jokes()
        val destination = "storage/test/${Random.instance().internet.slug()}.txt"
        val writer = FileSystemDomainFileWriter()

        writer.write(content.toByteArray(), destination)

        val wroteFile = File(destination)

        assertEquals(content, wroteFile.readText())
        wroteFile.delete()
    }

}
