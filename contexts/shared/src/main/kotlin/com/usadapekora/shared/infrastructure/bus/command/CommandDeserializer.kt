package com.usadapekora.shared.infrastructure.bus.command

import com.usadapekora.shared.domain.camelToSnakeCase
import com.usadapekora.shared.domain.bus.command.Command
import com.usadapekora.shared.infrastructure.serialization.createJacksonObjectMapperInstance

class CommandDeserializer(private val registry: CommandRegistry) {
	private val objectMapper = createJacksonObjectMapperInstance()

	fun deserialize(rawCommand: String): Command {
		val commands = registry.handlers.map { it.key }
		val commandName = objectMapper.readValue(rawCommand, CommandName::class.java)
		val commandClass = commands.singleOrNull { it.simpleName!!.camelToSnakeCase() == commandName.name }
			?: throw RuntimeException("Command class for command name ${commandName.name} not found")
		val type = objectMapper.typeFactory.constructParametricType(CommandJson::class.java, commandClass.java)
		val command = objectMapper.readValue(rawCommand, type) as CommandJson<Command>
		return command.attributes
	}

}
