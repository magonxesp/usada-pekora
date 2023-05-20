package com.usadapekora.shared

import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun enableDependencyInjection(modules: List<Module> = listOf()) {
    startKoin {
        modules(modules)
    }
}
