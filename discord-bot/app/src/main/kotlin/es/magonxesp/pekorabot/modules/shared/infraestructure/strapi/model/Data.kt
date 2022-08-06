package es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.model

import kotlinx.serialization.Serializable

@Serializable
class Data<ModelAttributes>(val data: ModelAttributes)
