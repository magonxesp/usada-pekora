package com.usadapekora.bot.application.trigger.update.audio

import kotlinx.serialization.Serializable

data class TriggerAudioResponseUrlUpdateRequest(
    override val id: String,
    val values: NewValues
): TriggerAudioResponseUpdateRequest {
    @Serializable
    data class NewValues(
        val type: String,
        /**
         * Path or url of the source audio
         */
        val source: String,
    )
}
