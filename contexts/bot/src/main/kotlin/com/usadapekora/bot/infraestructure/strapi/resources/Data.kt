package com.usadapekora.bot.infraestructure.strapi.resources

import kotlinx.serialization.Serializable

@Serializable
class Data<ModelAttributes>(val data: ModelAttributes)
