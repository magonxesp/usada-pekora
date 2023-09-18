package com.usadapekora.bot.discordbot

import com.usadapekora.shared.infrastructure.monitoring.MicrometerMonitoring
import com.usadapekora.shared.infrastructure.monitoring.createMetricName
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge

private var guildCount = object { var count = 0L }
private val guildCounter: Gauge = Gauge.builder(createMetricName("bot", "discord_bot", "discord_guilds_total"), guildCount) { it.count.toDouble() }
    .description("Current discord guilds using the bot")
    .register(MicrometerMonitoring.micrometerRegistry)

private val messagesCount: Counter = Counter.builder(createMetricName("bot", "discord_bot", "received_messages_total"))
    .description("Total of the discord messages received")
    .register(MicrometerMonitoring.micrometerRegistry)

private val processedMessagesCount: Counter = Counter.builder(createMetricName("bot", "discord_bot", "processed_messages_total"))
    .description("Total of the discord messages to be handled")
    .register(MicrometerMonitoring.micrometerRegistry)

private val commandFiredCount: Counter = Counter.builder(createMetricName("bot", "discord_bot", "command_fired_total"))
    .description("Total of the commands fired")
    .register(MicrometerMonitoring.micrometerRegistry)

fun registerDiscordGuildCount(count: Long) {
    guildCount.count = count
}

fun registerReceivedMessage() = messagesCount.increment()
fun registerProcessedMessage() = processedMessagesCount.increment()
fun registerCommandFired() = commandFiredCount.increment()
