package com.usadapekora.shared.domain

import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses

inline fun <reified T : Annotation> getAnnotation(klass: KClass<*>): T {
    val annotation = klass.annotations
        .firstOrNull { it is T }
        ?.let { it as T }

    return annotation ?: throw RuntimeException("The class $klass must have the ${T::class} annotation")
}

fun <T : Any> KClass<T>.isSubsclassOfAbstract(klass: KClass<*>) =
    allSuperclasses.singleOrNull { it.isAbstract && it == klass } != null
