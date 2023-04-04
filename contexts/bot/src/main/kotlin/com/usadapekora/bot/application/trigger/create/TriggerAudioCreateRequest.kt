package com.usadapekora.bot.application.trigger.create


class TriggerAudioCreateRequest(
    val content: ByteArray,
    val fileName: String,
    val id: String,
    val triggerId: String,
    val guildId: String,
)
