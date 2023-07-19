package com.usadapekora.bot.infraestructure.trigger.persistence.json

import kotlinx.serialization.Serializable

@Serializable
data class TriggerTextResponseJson(
    val id: String,
    val content: String,
    val type: String
)
