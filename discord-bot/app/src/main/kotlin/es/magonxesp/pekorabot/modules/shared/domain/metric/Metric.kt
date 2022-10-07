package es.magonxesp.pekorabot.modules.shared.domain.metric

enum class Metric(name: String) {
    DISCORD_MESSAGE("discord_message"),
    DISCORD_MESSAGE_PROCESSED("discord_message_processed"),
    DISCORD_TRIGGER_FIRED("discord_trigger_fired"),
    DISCORD_COMMAND_FIRED("discord_command_fired"),
}
