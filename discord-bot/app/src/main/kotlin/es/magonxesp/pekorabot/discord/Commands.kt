package es.magonxesp.pekorabot.discord

import es.magonxesp.pekorabot.discord.command.FeedCommand
import kotlin.reflect.KClass


val commands: Array<KClass<*>> = arrayOf(
    FeedCommand::class
)
