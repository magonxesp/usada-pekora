package com.usadapekora.context.application.trigger.update

data class TriggerUpdateRequest(
    val id: String,
    val values: NewValues
) {
    data class NewValues(
        val input: String? = null,
        val compare: String? = null,
        val outputText: String? = null,
        val discordGuildId: String? = null
    )
}
