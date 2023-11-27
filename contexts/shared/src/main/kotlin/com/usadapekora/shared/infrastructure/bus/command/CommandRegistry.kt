package com.usadapekora.shared.infrastructure.bus.command

import com.usadapekora.shared.CommandHandlers

class CommandRegistry {
	companion object {
		private var registeredCommandHandlers: CommandHandlers = mapOf()
	}

	fun registerCommandHandlers(handlers: CommandHandlers) {
		registeredCommandHandlers = handlers
	}

	val handlers: CommandHandlers
		get() = registeredCommandHandlers
}
