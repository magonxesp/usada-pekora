package com.usadapekora.context.shared.infraestructure.strapi.resources

import kotlinx.serialization.Serializable

@Serializable
class Collection<Model>(val data: Array<Model>)
