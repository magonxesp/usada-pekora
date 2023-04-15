package com.usadapekora.bot.application.trigger.update.text

data class TriggerTextResponseUpdateRequest(
    val id: String,
    val values: NewValues
) {
    data class NewValues(
        val content: String? = null,
        val type: String? = null
    )
}
