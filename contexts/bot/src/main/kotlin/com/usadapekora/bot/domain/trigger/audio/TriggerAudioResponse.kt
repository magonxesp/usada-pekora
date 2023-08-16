package com.usadapekora.bot.domain.trigger.audio

import com.usadapekora.shared.domain.Entity

data class TriggerAudioResponse(
    val id: TriggerAudioResponseId,
    var source: TriggerAudioResponseSource,
    var sourceUri: TriggerAudioResponseSourceUri
) : Entity() {
    enum class TriggerAudioResponseSource {
        FILE, RESOURCE
    }
    data class TriggerAudioResponseSourceUri(val value: String)

    // TODO: update trigger audio response creator and repositories

    companion object {
        fun fromPrimitives(
            id: String,
            source: String,
            sourceUri: String
        ): TriggerAudioResponse = TriggerAudioResponse(
            id = TriggerAudioResponseId(id),
            source = TriggerAudioResponseSource.valueOf(source.uppercase()),
            sourceUri = TriggerAudioResponseSourceUri(sourceUri)
        )
    }

    override fun id(): String = id.value
}
