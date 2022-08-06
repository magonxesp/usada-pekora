package es.magonxesp.pekorabot.modules.trigger.infraestructure.strapi.trigger

import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.model.Model
import kotlinx.serialization.Serializable
import es.magonxesp.pekorabot.modules.trigger.domain.Trigger as TriggerAggregate

@Serializable
data class TriggerModel (
    val id: Int,
    val attributes: TriggerAttributes
): Model() {
    fun toAggregate(): TriggerAggregate = TriggerAggregate.fromPrimitives(
        input = attributes.input,
        compare = attributes.compare,
        outputText = attributes.output_text,
        outputSound = if (attributes.output_audio != null) attributes.output_audio.data[0].attributes.url else null
    )
}
