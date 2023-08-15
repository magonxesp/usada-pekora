package com.usadapekora.bot.application.trigger.create

import kotlinx.serialization.Serializable

@Serializable
data class TriggerCreateRequest(
    val id: String,
    val title: String,
    val input: String,
    val compare: String,
    val guildId: String,
    val responseTextId: String? = null,
    val responseAudioId: String? = null,
    val responseAudioProvider: String? = null
)
