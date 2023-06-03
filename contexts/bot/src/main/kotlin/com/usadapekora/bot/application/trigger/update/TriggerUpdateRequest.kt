package com.usadapekora.bot.application.trigger.update

import kotlinx.serialization.Serializable

data class TriggerUpdateRequest(
    val id: String,
    val values: NewValues
) {
    @Serializable
    data class NewValues(
        val title: String? = null,
        val input: String? = null,
        val compare: String? = null,
        val responseTextId: String? = null,
        val responseAudioId: String? = null,
        val discordGuildId: String? = null,
        val responseAudioProvider: String? = null
    )
}
