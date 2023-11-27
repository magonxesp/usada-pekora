package com.usadapekora.shared.infrastructure.bus.command

import arrow.core.Either
import com.usadapekora.shared.domain.bus.command.Command
import com.usadapekora.shared.domain.camelToSnakeCase
import com.usadapekora.shared.infrastructure.serialization.createJacksonObjectMapperInstance

class CommandSerializer {
    private val objectMapper = createJacksonObjectMapperInstance()

    fun serialize(command: Command) = Either.catch {
        objectMapper.writeValueAsString(
            CommandJson(
                name = command::class.simpleName!!.camelToSnakeCase(),
                attributes = command
            )
        )
    }

}
