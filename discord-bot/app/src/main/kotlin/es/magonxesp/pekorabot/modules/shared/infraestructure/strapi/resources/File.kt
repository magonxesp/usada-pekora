package es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.resources

import kotlinx.serialization.Serializable

@Serializable
data class File(
    val id: Int,
    val attributes: FileAttributes
) {
    @Serializable
    data class FileAttributes(
        val url: String
    )
}
