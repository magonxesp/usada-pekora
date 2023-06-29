package com.usadapekora.shared.infrastructure.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun createJacksonObjectMapperInstance() = ObjectMapper().apply {
    registerKotlinModule()
    registerModule(JavaTimeModule())
}
