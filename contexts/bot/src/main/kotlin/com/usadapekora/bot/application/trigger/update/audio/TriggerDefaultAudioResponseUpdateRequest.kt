package com.usadapekora.bot.application.trigger.update.audio

import kotlinx.serialization.Serializable

data class TriggerDefaultAudioResponseUpdateRequest(
    val id: String,
    val values: NewValues
) {
    @Serializable
    data class NewValues(
        val triggerId: String? = null,
        val guildId: String? = null,
        val fileName: String? = null,
        val content: ByteArray? = null
    )
}
