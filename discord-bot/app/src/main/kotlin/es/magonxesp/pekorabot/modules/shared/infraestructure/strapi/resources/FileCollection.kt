package es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.resources


import kotlinx.serialization.Serializable

@Serializable
class FileCollection(val data: Array<File>)
