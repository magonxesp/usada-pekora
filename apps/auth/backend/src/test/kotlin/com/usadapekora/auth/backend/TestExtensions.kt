package com.usadapekora.auth.backend

fun String.uglifyJson(): String
    = trimIndent().replace(Regex("\\s(?=[\\s\":{}\\[\\](null)])"), "")
