package es.magonxesp.pekorabot.discord.shared

import kotlin.reflect.KClass

data class CommandArgument<T>(
    val name: String,
    val type: KClass<*>,
    val required: Boolean = false,
    val default: T? = null
)
