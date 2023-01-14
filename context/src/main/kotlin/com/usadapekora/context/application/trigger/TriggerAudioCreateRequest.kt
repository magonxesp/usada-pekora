package com.usadapekora.context.application.trigger

import java.io.File

class TriggerAudioCreateRequest(
    val id: String,
    val triggerId: String,
    val guildId: String,
    val fileName: String,
    val fileContent: Array<Byte>,
)
