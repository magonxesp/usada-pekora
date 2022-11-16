package com.usadapekora.context.trigger.infraestructure.persistence.strapi

import com.usadapekora.context.shared.infraestructure.strapi.resources.ModelAttributes
import kotlinx.serialization.Serializable
import com.usadapekora.context.shared.infraestructure.strapi.resources.FileData

@Serializable
data class TriggerAttributes (
    val input: String,
    val compare: String,
    val output_text: String?,
    val output_audio: FileData?
) : ModelAttributes()
