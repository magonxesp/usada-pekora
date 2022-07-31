package es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.file


import kotlinx.serialization.Serializable

@Serializable
class FileList(val data: Array<File>)
