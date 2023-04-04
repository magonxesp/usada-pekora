package com.usadapekora.bot.infraestructure.strapi.resources


import kotlinx.serialization.Serializable

@Serializable
class FileCollection(val data: Array<File>)
