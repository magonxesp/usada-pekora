package com.usadapekora.bot.application.trigger.create

data class TriggerCreateRequest(
    val id: String,
    val title: String,
    val input: String,
    val compare: String,
    val discordGuildId: String,
    val responseTextId: String?,
    val responseAudioId: String?,
    val responseAudioProvider: String?,
)
