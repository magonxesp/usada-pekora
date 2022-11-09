package com.usadapekora.context.shared.infraestructure.strapi.resources

import kotlinx.serialization.Serializable

@Serializable
class Data<ModelAttributes>(val data: ModelAttributes)
