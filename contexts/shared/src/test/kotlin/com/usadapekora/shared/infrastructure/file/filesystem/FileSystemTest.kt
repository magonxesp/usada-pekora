package com.usadapekora.shared.infrastructure.file.filesystem

import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.test.BeforeTest

abstract class FileSystemTest {

    @BeforeTest
    fun createDirectory() {
        Path("storage/test").createDirectories()
    }

}
