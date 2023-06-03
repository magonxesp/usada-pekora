package com.usadapekora.bot.application.trigger.create.text

import kotlinx.serialization.Serializable

@Serializable
data class TriggerTextResponseCreateRequest(
    val id: String,
    val content: String,
    val type: String
)
