package com.usadapekora.bot.application.trigger.find.text

import kotlinx.serialization.Serializable

@Serializable
data class TriggerTextResponseFindResponse(
    val id: String,
    val content: String,
    val type: String
)
