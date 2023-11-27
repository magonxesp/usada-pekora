package com.usadapekora.shared.infrastructure.bus.command

import arrow.core.Either
import com.usadapekora.shared.domain.bus.command.Command
import com.usadapekora.shared.domain.bus.command.CommandBus

class InMemoryCommandBus(private val commandDispatcher: CommandDispatcher) : CommandBus {
	override fun dispatch(command: Command): Either<Exception, Unit> =
        commandDispatcher.dispatch(command)
}
