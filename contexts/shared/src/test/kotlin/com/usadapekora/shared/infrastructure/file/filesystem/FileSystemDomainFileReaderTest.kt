package com.usadapekora.shared.infrastructure.file.filesystem

import com.usadapekora.shared.domain.Random
import com.usadapekora.shared.infrastructure.file.filesystem.FileSystemDomainFileReader
import java.io.File
import kotlin.io.path.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class FileSystemDomainFileReaderTest : FileSystemTest() {

    @Test
    fun `should read a file by its source`() {
        val reader = FileSystemDomainFileReader()
        val expected = Random.instance().chiquito.jokes()
        val source = Path("storage/test", "${Random.instance().internet.slug()}.txt").toString()
        val file = File(source)

        file.writeText(expected)
        val content = reader.read(source).getOrNull()!!

        assertEquals(expected, content.decodeToString())
        file.delete()
    }

}
