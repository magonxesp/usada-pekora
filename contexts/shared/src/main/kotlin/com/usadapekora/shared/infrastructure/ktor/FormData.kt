package com.usadapekora.shared.infrastructure.ktor

import io.ktor.http.content.*

class FormData(private val data: Map<String, PartData>) {

    fun getString(name: String): String? {
        if (!data.containsKey(name)) {
            return null
        }

        val part = data[name]

        if (part is PartData.FormItem) {
            return part.value
        }

        return null
    }

    fun getFile(name: String): MultipartFile? {
        if (!data.containsKey(name)) {
            return null
        }

        val part = data[name]

        if (part is PartData.FileItem) {
            return MultipartFile(
                fileName = part.originalFileName ?: "",
                content = part.streamProvider().readAllBytes()
            )
        }

        return null
    }

}
