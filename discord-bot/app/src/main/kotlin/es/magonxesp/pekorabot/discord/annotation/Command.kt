package es.magonxesp.pekorabot.discord.annotation

@Target(AnnotationTarget.FUNCTION)
annotation class Command(
    val command: String,
    val description: String = "",
    val usage: String = ""
)
