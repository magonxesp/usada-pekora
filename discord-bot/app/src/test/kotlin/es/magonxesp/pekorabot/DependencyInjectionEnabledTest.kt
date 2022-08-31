package es.magonxesp.pekorabot

import org.koin.core.module.Module
import kotlin.test.BeforeTest

abstract class DependencyInjectionEnabledTest {

    @BeforeTest
    fun beforeTest() {
        enableDependencyInjection(testModules())
    }

    open fun testModules(): List<Module> = listOf()
}
