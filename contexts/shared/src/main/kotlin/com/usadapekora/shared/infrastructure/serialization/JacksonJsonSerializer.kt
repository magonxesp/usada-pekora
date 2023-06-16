package com.usadapekora.shared.infrastructure.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

fun createJacksonObjectMapperInstance() = ObjectMapper().apply {
    registerModule(JavaTimeModule())
}
