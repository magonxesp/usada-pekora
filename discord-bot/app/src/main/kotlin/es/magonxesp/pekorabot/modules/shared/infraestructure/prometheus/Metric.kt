package es.magonxesp.pekorabot.modules.shared.infraestructure.prometheus

enum class Metric(val value: String) {
    DISCORD_MESSAGE("pekorabot_discord_message_total"),
    DISCORD_MESSAGE_PROCESSED("pekorabot_discord_message_processed_total"),
    DISCORD_TRIGGER_FIRED("pekorabot_discord_trigger_fired_total"),
    DISCORD_COMMAND_FIRED("pekorabot_discord_command_fired_total"),
    DISCORD_GUILDS("pekorabot_discord_guilds_total"),
}
