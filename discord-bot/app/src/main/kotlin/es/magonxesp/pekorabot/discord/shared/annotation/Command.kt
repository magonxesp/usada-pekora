package es.magonxesp.pekorabot.discord.shared.annotation


@Target(AnnotationTarget.CLASS)
annotation class Command(
    val command: String,
    val description: String = "",
    val usage: String = "",
)
