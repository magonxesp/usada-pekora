package com.usadapekora.shared.infrastructure.bus.command

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.usadapekora.shared.domain.bus.command.Command

@JsonIgnoreProperties(ignoreUnknown = true)
open class CommandName(
	open val commandName: String
)

data class CommandJson<T : Command>(
    override val commandName: String,
    val attributes: T
) : CommandName(commandName)
