package es.magonxesp.pekorabot.modules.trigger.infraestructure.strapi.trigger

import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.resources.File
import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.resources.FileCollection
import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.resources.Model
import kotlinx.serialization.Serializable
import es.magonxesp.pekorabot.modules.trigger.domain.Trigger

@Serializable
data class TriggerModel (
    val id: Int,
    val attributes: TriggerAttributes
): Model() {
    companion object {
        fun fromAggregate(trigger: Trigger): TriggerModel = TriggerModel(
            id = trigger.id.toInt(),
            attributes = TriggerAttributes(
                input = trigger.input,
                compare = trigger.compare.value,
                output_text = trigger.outputText,
                output_audio = null // TODO: resolve output audio from aggregate to model
            )
        )
    }

    fun toAggregate(): Trigger = Trigger.fromPrimitives(
        id = id.toString(),
        input = attributes.input,
        compare = attributes.compare,
        outputText = attributes.output_text,
        outputSound = attributes.output_audio?.data?.first()?.attributes?.url
    )
}
