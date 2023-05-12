package com.usadapekora.shared

import io.github.cdimascio.dotenv.dotenv
import java.io.File

private val dotenv = dotenv {
    filename = ".env"
    ignoreIfMissing = true
}

fun env(key: String, defaultValue: String = ""): String {
    val file = dotenv["${key}_FILE"].takeIf { it != null }?.let {
        File(it)
    }

    if (file != null && file.exists()) {
        return file.readText().trim()
    }

    return dotenv[key] ?: defaultValue
}
