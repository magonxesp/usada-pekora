package com.usadapekora.context.trigger.infraestructure.strapi.trigger

import com.usadapekora.backendBaseUrl
import com.usadapekora.context.shared.infraestructure.strapi.resources.Model
import kotlinx.serialization.Serializable
import com.usadapekora.context.trigger.domain.Trigger

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
        outputSound = attributes.output_audio?.let {
            backendBaseUrl + it.data.attributes.url
        }
    )
}
