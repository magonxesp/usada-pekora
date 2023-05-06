package com.usadapekora.bot.application.trigger.update.audio

data class TriggerDefaultAudioResponseUpdateRequest(
    val id: String,
    val values: NewValues
) {
    data class NewValues(
        val triggerId: String? = null,
        val guildId: String? = null,
        val fileName: String? = null,
        val content: ByteArray? = null
    )
}
