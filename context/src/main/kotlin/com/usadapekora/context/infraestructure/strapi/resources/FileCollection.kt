package com.usadapekora.context.infraestructure.strapi.resources


import kotlinx.serialization.Serializable

@Serializable
class FileCollection(val data: Array<File>)
