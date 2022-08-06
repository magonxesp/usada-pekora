package es.magonxesp.pekorabot.modules.trigger.infraestructure.strapi.trigger

import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.resources.ModelAttributes
import kotlinx.serialization.Serializable
import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.resources.FileCollection

@Serializable
data class TriggerAttributes (
    val input: String,
    val compare: String,
    val output_text: String?,
    val output_audio: FileCollection?
) : ModelAttributes()
