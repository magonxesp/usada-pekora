package com.usadapekora.bot.infraestructure.trigger.persistence.json

import kotlinx.serialization.Serializable

@Serializable
data class TriggerBuiltInAudioResponseJson(
    val id: String,
    val fileName: String
)
