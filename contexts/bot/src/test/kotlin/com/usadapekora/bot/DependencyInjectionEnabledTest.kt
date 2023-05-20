package com.usadapekora.bot

import com.usadapekora.shared.enableDependencyInjection
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

abstract class DependencyInjectionEnabledTest {
    protected fun setupTestModules(testServices: () -> List<Module> = { listOf() }) {
        val testModules = testServices()

        stopKoin()
        enableDependencyInjection(modules = modules.plus(testModules))
    }
}
