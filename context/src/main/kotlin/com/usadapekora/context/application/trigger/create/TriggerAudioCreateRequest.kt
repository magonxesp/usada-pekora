package com.usadapekora.context.application.trigger.create

import java.io.File

class TriggerAudioCreateRequest(
    val id: String,
    val triggerId: String,
    val guildId: String,
    val fileName: String,
    val fileContent: ByteArray,
)
