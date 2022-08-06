package es.magonxesp.pekorabot.modules.trigger.infraestructure.strapi.trigger

import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.resources.Model
import kotlinx.serialization.Serializable
import es.magonxesp.pekorabot.modules.trigger.domain.Trigger

@Serializable
data class TriggerModel (
    val id: Int,
    val attributes: TriggerAttributes
): Model() {
    fun toAggregate(): Trigger = Trigger.fromPrimitives(
        input = attributes.input,
        compare = attributes.compare,
        outputText = attributes.output_text,
        outputSound = attributes.output_audio?.data?.first()?.attributes?.url
    )
}
