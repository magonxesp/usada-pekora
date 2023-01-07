package com.usadapekora.context.trigger.application.create

import java.io.File

data class TriggerAudioCreateRequest(
    val id: String,
    val triggerId: String,
    val file: File
)
