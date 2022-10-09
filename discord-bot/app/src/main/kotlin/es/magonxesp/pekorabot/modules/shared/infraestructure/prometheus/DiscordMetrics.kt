package es.magonxesp.pekorabot.modules.shared.infraestructure.prometheus

import es.magonxesp.pekorabot.appEnv
import es.magonxesp.pekorabot.modules.shared.domain.KeyValueCacheStorage
import io.prometheus.client.Counter
import io.prometheus.client.Gauge
import org.koin.java.KoinJavaComponent.inject

val cache: KeyValueCacheStorage by inject(KeyValueCacheStorage::class.java)


val messagesCount = Counter.build()
    .name("pekorabot_discord_message_total")
    .help("Total of the incoming discord messages")
    .labelNames("env")
    .register()

val processedMessagesCount: Counter = Counter.build()
    .name("pekorabot_discord_message_processed_total")
    .help("Total of the discord messages to be handled")
    .labelNames("env")
    .register()

val triggerFiredCount: Counter = Counter.build()
    .name("pekorabot_discord_trigger_fired_total")
    .help("Total of the triggers fired")
    .labelNames("env")
    .register()

val commandFiredCount: Counter = Counter.build()
    .name("pekorabot_discord_command_fired_total")
    .help("Total of the commands fired")
    .labelNames("env")
    .register()

val guildCount: Gauge = Gauge.build()
    .name("pekorabot_discord_guilds_total")
    .help("Current discord guilds using the bot")
    .labelNames("env")
    .register()

fun registerMessageRequest() {
    messagesCount.labels(appEnv).inc()
}

fun registerProccesedMessageRequest() {
    processedMessagesCount.labels(appEnv).inc()
}

fun registerTriggerFired() {
    triggerFiredCount.labels(appEnv).inc()
}

fun registerCommandFired() {
    commandFiredCount.labels(appEnv).inc()
}

fun registerGuildCount(count: Long) {
    guildCount.labels(appEnv).set(count.toDouble())
}
