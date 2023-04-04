package com.usadapekora.bot.discordbot

import com.usadapekora.bot.discordbot.command.FeedCommand
import kotlin.reflect.KClass


val commands: Array<KClass<*>> = arrayOf(
    FeedCommand::class
)
