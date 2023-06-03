package com.usadapekora.bot.application.trigger.create.audio

import kotlinx.serialization.Serializable

@Serializable
class TriggerDefaultAudioResponseCreateRequest(
    val content: ByteArray,
    val fileName: String,
    val id: String,
    val triggerId: String,
    val guildId: String,
)
