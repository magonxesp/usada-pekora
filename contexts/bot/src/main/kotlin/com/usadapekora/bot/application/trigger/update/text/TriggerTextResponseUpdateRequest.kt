package com.usadapekora.bot.application.trigger.update.text

import kotlinx.serialization.Serializable

data class TriggerTextResponseUpdateRequest(
    val id: String,
    val values: NewValues
) {
    @Serializable
    data class NewValues(
        val content: String? = null,
        val type: String? = null
    )
}
