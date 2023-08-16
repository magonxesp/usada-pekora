package com.usadapekora.auth.infrastructure.jwt.auth0

fun String.trimKey(): String
    = split("\n", "\r")
    .filter { !Regex("^-+[A-Z ]+-+\$").matches(it) }
    .joinToString("")
