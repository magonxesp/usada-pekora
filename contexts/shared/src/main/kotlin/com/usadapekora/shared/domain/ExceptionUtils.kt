package com.usadapekora.shared.domain

inline fun <reified E : Throwable, T>tryOrNull(returnValue: () -> T): T?
    = try { returnValue() } catch (exception: Throwable) { if (exception is E) null else throw exception }
