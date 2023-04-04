package com.usadapekora.bot.infraestructure.strapi.resources

import kotlinx.serialization.Serializable

@Serializable
class Collection<Model>(val data: Array<Model>)
