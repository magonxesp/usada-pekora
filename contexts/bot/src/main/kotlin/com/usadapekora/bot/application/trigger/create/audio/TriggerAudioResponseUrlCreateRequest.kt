package com.usadapekora.bot.application.trigger.create.audio

data class TriggerAudioResponseUrlCreateRequest(
    override val id: String,
    val type: String,
    /**
     * Path or url of the source audio
     */
    val source: String,
) : TriggerAudioResponseCreateRequest
