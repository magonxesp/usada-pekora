package com.usadapekora.bot.application.trigger.create.audio

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseContent

class TriggerAudioResponseCreateRequest(
    val id: String,
    val triggerId: String,
    val guildId: String,
    val content: TriggerAudioResponseContent
)
