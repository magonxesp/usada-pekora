package com.usadapekora.context.trigger.infraestructure.persistence.strapi

import com.usadapekora.context.strapiBaseUrl
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
            id = trigger.id.value.toInt(),
            attributes = TriggerAttributes(
                input = trigger.input.value,
                compare = trigger.compare.value,
                discord_server_id = trigger.discordGuildId.value,
                output_text = trigger.outputText.value,
                output_audio = null // TODO: resolve output audio from aggregate to model
            )
        )
    }

    fun toAggregate(): Trigger = Trigger.fromPrimitives(
        id = id.toString(),
        input = attributes.input,
        compare = attributes.compare,
        outputText = attributes.output_text,
        discordGuildId = attributes.discord_server_id
    )
}
