package es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.resources

import kotlinx.serialization.Serializable

@Serializable
class Collection<Model>(val data: Array<Model>)