package com.usadapekora.bot.backend

fun String.uglifyJson(): String
    = trimIndent().replace(Regex("\\s(?=[\\s\":{}\\[\\]])"), "")
