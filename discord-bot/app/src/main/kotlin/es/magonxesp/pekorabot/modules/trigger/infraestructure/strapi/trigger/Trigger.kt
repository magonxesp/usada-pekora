package es.magonxesp.pekorabot.modules.trigger.infraestructure.strapi.trigger

import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.file.FileList
import kotlinx.serialization.Serializable
import es.magonxesp.pekorabot.modules.trigger.domain.Trigger as TriggerAggregate

@Serializable
data class Trigger(
    val id: Int,
    val attributes: TriggerAttributes
) {

    @Serializable
    data class TriggerAttributes(
        val input: String,
        val compare: String,
        val output_text: String?,
        val output_audio: FileList?
    )
    
    fun toAggregate(): TriggerAggregate = TriggerAggregate.fromPrimitives(
        input = attributes.input,
        compare = attributes.compare,
        outputText = attributes.output_text,
        outputSound = if (attributes.output_audio != null) attributes.output_audio.data[0].attributes.url else null
    )
}
