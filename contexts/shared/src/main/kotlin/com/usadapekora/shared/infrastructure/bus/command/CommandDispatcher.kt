package com.usadapekora.shared.infrastructure.bus.command

import arrow.core.Either
import arrow.core.left
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.domain.bus.command.Command
import com.usadapekora.shared.domain.bus.command.CommandHandler
import com.usadapekora.shared.serviceContainer

class CommandDispatcher(
    private val registry: CommandRegistry,
    loggerFactory: LoggerFactory
) {
	private val logger = loggerFactory.getLogger(this::class)

	fun dispatch(command: Command): Either<Exception, Unit> {
		val handler = registry.handlers[command::class] ?:
			return RuntimeException("Failed to handle command ${command::class} because it don't have a handler").left()
		val instance = serviceContainer().get<CommandHandler<Command>>(handler)

		return instance.handle(command).onLeft {
			logger.warning("Command ${command::class} failed: ${it.message}")
		}.onRight {
			logger.info("Command ${command::class} consumed")
		}
	}
}
