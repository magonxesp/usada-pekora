package com.usadapekora.auth.backend

private fun String.trimKey(): String
    = split("\n")
    .filter { !Regex("^-+[A-Z ]+-+\$").matches(it) }
    .joinToString("")
