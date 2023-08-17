package com.usadapekora.bot.application.trigger.update.audio

import kotlinx.serialization.Serializable

data class TriggerAudioResponseUpdateRequest(
    val id: String,
    val values: NewValues
) {
    @Serializable
    data class NewValues(
        val triggerId: String,
        val guildId: String,
        val fileName: String,
        val content: ByteArray
    )
}
