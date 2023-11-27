package com.usadapekora.shared.domain.bus.command

import arrow.core.Either

interface CommandHandler<T : Command> {
    fun handle(command: T): Either<Exception, Unit>
}
