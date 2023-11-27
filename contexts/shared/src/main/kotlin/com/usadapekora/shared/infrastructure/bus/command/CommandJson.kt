package com.usadapekora.shared.infrastructure.bus.command

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.usadapekora.shared.domain.bus.command.Command

@JsonIgnoreProperties(ignoreUnknown = true)
open class CommandName(
	open val name: String
)

data class CommandJson<T : Command>(
	override val name: String,
	val attributes: T
) : CommandName(name)
