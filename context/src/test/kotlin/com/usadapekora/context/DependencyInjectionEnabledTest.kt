package com.usadapekora.context

import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import com.usadapekora.context.enableDependencyInjection

abstract class DependencyInjectionEnabledTest {
    protected fun setupTestModules(testServices: () -> List<Module> = { listOf() }) {
        val testModules = testServices()

        stopKoin()
        enableDependencyInjection(testModules)
    }
}
