package com.usadapekora.bot

import io.mockk.clearAllMocks
import kotlin.test.BeforeTest

abstract class TestCase {
    @BeforeTest
    fun resetAllMocks() = clearAllMocks()
}
