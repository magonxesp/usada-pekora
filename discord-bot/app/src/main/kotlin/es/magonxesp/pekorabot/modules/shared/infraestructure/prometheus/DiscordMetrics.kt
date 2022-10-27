package es.magonxesp.pekorabot.modules.shared.infraestructure.prometheus

import io.prometheus.client.Counter
import io.prometheus.client.Gauge

val messagesCount: Counter = Counter.build()
    .name("pekorabot_discord_message_total")
    .help("Total of the incoming discord messages")
    .register()

val processedMessagesCount: Counter = Counter.build()
    .name("pekorabot_discord_message_processed_total")
    .help("Total of the discord messages to be handled")
    .register()

val triggerFiredCount: Counter = Counter.build()
    .name("pekorabot_discord_trigger_fired_total")
    .help("Total of the triggers fired")
    .register()

val commandFiredCount: Counter = Counter.build()
    .name("pekorabot_discord_command_fired_total")
    .help("Total of the commands fired")
    .register()

val guildCount: Gauge = Gauge.build()
    .name("pekorabot_discord_guilds_total")
    .help("Current discord guilds using the bot")
    .register()

fun registerMessageRequest() {
    messagesCount.inc()
}

fun registerProccesedMessageRequest() {
    processedMessagesCount.inc()
}

fun registerTriggerFired() {
    triggerFiredCount.inc()
}

fun registerCommandFired() {
    commandFiredCount.inc()
}

fun registerGuildCount(count: Long) {
    guildCount.set(count.toDouble())
}
