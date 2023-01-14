package com.usadapekora.context.infraestructure.strapi.resources

import kotlinx.serialization.Serializable

@Serializable
class Data<ModelAttributes>(val data: ModelAttributes)
