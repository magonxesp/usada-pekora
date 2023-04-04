package com.usadapekora.bot.infraestructure.strapi.trigger

import com.usadapekora.bot.infraestructure.strapi.resources.ModelAttributes
import kotlinx.serialization.Serializable
import com.usadapekora.bot.infraestructure.strapi.resources.FileData

@Serializable
data class TriggerAttributes (
    val input: String,
    val compare: String,
    val discord_server_id: String,
    val output_text: String?,
    val output_audio: FileData?
) : ModelAttributes()
