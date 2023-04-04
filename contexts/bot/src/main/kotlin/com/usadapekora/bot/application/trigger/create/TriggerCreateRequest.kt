package com.usadapekora.bot.application.trigger.create

data class TriggerCreateRequest(
    val id: String,
    val title: String,
    val input: String,
    val compare: String,
    val outputText: String?,
    val discordGuildId: String
)
