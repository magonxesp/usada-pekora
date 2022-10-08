package es.magonxesp.pekorabot.modules.shared.infraestructure.prometheus

import es.magonxesp.pekorabot.appEnv
import es.magonxesp.pekorabot.modules.shared.domain.KeyValueCacheStorage
import io.prometheus.client.Counter
import io.prometheus.client.Gauge
import org.koin.java.KoinJavaComponent.inject

val cache by inject<KeyValueCacheStorage>(KeyValueCacheStorage::class.java)

val messagesCount: Counter = Counter.build()
    .name(Metric.DISCORD_MESSAGE.value)
    .labelNames("env")
    .register()

val processedMessagesCount: Counter = Counter.build()
    .name(Metric.DISCORD_MESSAGE_PROCESSED.value)
    .labelNames("env")
    .register()

val triggerFiredCount: Counter = Counter.build()
    .name(Metric.DISCORD_TRIGGER_FIRED.value)
    .labelNames("env")
    .register()

val commandFiredCount: Counter = Counter.build()
    .name(Metric.DISCORD_COMMAND_FIRED.value)
    .labelNames("env")
    .register()

val guildCount: Gauge = Gauge.build()
    .name(Metric.DISCORD_GUILDS.value)
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

fun registerGuildCount(guildId: String) {
    val guilds = (cache.get("discord_guilds") ?: "").split(";").toMutableList()

    if (guildId in guilds) {
        guilds.add(guildId)
    }

    cache.set("discord_guilds", guilds.joinToString(";"))
    guildCount.labels(appEnv).set(guilds.count().toDouble())
}
