package com.usadapekora.shared.domain

import kotlin.reflect.KClass

inline fun <reified T : Annotation> getAnnotation(klass: KClass<*>): T = klass.annotations
    .firstOrNull { it is T }
    ?.let { it as T }
    ?: throw RuntimeException("The class $klass must have the ${T::class} annotation")
