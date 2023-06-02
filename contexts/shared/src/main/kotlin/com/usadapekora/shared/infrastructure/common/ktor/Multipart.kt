package com.usadapekora.shared.infrastructure.common.ktor

import io.ktor.http.content.*

suspend fun MultiPartData.toFormData(): FormData {
    val data = mutableMapOf<String, PartData>()

    forEachPart { part ->
        if (!part.name.isNullOrBlank()) {
            data[part.name!!] = part
        }
    }
    /*when(part) {
        is PartData.FormItem -> part.value
        is PartData.FileItem -> part.streamProvider().readAllBytes()
        else -> null
    }*/
    return FormData(data)
}

