package com.usadapekora.bot.application.trigger.create.audio

data class TriggerAudioResponseFileCreateRequest(
    override val id: String,
    val triggerId: String,
    val guildId: String,
    val fileName: String,
    val fileContent: ByteArray
) : TriggerAudioResponseCreateRequest
