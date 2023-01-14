package com.usadapekora.context.application.trigger.create

data class TriggerCreateRequest(
    val id: String,
    val input: String,
    val compare: String,
    val outputText: String?,
    val discordGuildId: String
)