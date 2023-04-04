package com.usadapekora.bot.application.trigger.update

data class TriggerUpdateRequest(
    val id: String,
    val values: NewValues
) {
    data class NewValues(
        val title: String? = null,
        val input: String? = null,
        val compare: String? = null,
        val outputText: String? = null,
        val discordGuildId: String? = null
    )
}
