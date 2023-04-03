package com.usadapekora.context.infraestructure.filesystem

import com.usadapekora.context.domain.Random
import java.io.File
import kotlin.test.Test
import kotlin.test.assertFalse

class FileSystemDomainFileDeleterTest : FileSystemTest() {

    @Test
    fun `should delete file contents to destination`() {
        val content = Random.instance().chiquito.jokes()
        val destination = "storage/test/${Random.instance().internet.slug()}.txt"
        val deleter = FileSystemDomainFileDeleter()
        val existingFile = File(destination)

        existingFile.writeText(content)
        deleter.delete(destination)

        assertFalse(existingFile.exists())
    }

}
