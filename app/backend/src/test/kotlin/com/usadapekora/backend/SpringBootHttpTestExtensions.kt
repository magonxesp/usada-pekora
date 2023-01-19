package com.usadapekora.backend

fun String.uglifyJson(): String
    = trimIndent().replace(Regex("\\s(?=[\\s\":{}])"), "")
