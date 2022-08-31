package es.magonxesp.pekorabot

import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import kotlin.test.BeforeTest

abstract class DependencyInjectionEnabledTest {
    protected fun setupTestModules(testServices: () -> List<Module>) {
        val testModules = testServices()

        stopKoin()
        enableDependencyInjection(testModules)
    }
}
