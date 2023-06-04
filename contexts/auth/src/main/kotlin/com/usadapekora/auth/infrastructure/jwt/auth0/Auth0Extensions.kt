package com.usadapekora.auth.infrastructure.jwt.auth0

fun String.trimKey(): String
    = split("\n")
    .filter { !Regex("^-+[A-Z ]+-+\$").matches(it) }
    .joinToString("")
