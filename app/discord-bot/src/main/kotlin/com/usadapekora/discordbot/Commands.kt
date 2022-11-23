package com.usadapekora.discordbot

import com.usadapekora.discordbot.command.FeedCommand
import kotlin.reflect.KClass


val commands: Array<KClass<*>> = arrayOf(
    FeedCommand::class
)
