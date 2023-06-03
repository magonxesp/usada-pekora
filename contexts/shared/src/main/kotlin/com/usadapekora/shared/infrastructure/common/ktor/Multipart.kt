package com.usadapekora.shared.infrastructure.common.ktor

import io.ktor.http.content.*

suspend fun MultiPartData.toFormData(): FormData {
    val data = mutableMapOf<String, PartData>()

    forEachPart { part ->
        if (!part.name.isNullOrBlank()) {
            data[part.name!!] = part
        }
    }

    return FormData(data)
}

