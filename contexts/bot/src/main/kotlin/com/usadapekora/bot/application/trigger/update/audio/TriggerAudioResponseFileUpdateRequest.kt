package com.usadapekora.bot.application.trigger.update.audio

import kotlinx.serialization.Serializable

data class TriggerAudioResponseFileUpdateRequest(
    override val id: String,
    val values: NewValues
): TriggerAudioResponseUpdateRequest {
    @Serializable
    data class NewValues(
        val triggerId: String,
        val guildId: String,
        val fileName: String,
        val fileContent: ByteArray
    )
}
