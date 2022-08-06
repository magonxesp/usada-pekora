package es.magonxesp.pekorabot.modules.trigger.infraestructure.strapi.trigger

import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.file.FileList
import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.model.ModelAttributes
import kotlinx.serialization.Serializable

@Serializable
data class TriggerAttributes (
    val input: String,
    val compare: String,
    val output_text: String?,
    val output_audio: FileList?
) : ModelAttributes()
