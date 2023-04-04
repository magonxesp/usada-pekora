package com.usadapekora.bot.infraestructure.strapi.client

import com.usadapekora.bot.infraestructure.strapi.resources.Collection
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class StrapiResponse(val json: String) {
    val serializer = Json {
        ignoreUnknownKeys = true
    }

    inline fun <reified Model> toModelCollection(): Collection<Model> {
        return serializer.decodeFromString(json)
    }
}
