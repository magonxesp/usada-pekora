package com.usadapekora.shared.domain.bus.command

import arrow.core.Either

interface CommandConsumer {
    fun consume(): Either<Exception, Unit>
}
