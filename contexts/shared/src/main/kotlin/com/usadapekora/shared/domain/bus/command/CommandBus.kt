package com.usadapekora.shared.domain.bus.command

import arrow.core.Either

interface CommandBus {
    fun dispatch(command: Command): Either<Exception, Unit>
}
