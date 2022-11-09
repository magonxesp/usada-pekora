package com.usadapekora.discord

import com.usadapekora.discord.command.FeedCommand
import kotlin.reflect.KClass


val commands: Array<KClass<*>> = arrayOf(
    FeedCommand::class
)
