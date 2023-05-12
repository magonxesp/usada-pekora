package com.usadapekora.bot.discordbot.command

import discord4j.core.`object`.entity.Message
import com.usadapekora.bot.discordbot.shared.CommandHandler
import com.usadapekora.bot.discordbot.shared.annotation.Command
import com.usadapekora.bot.discordbot.shared.CommandArgument
import com.usadapekora.bot.application.guild.GuildPreferenceCreator
import com.usadapekora.bot.application.guild.GuildPreferenceDeleter
import com.usadapekora.bot.application.guild.GuildPreferencesFinder
import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.domain.guild.GuildPreferencesException
import kotlinx.coroutines.reactor.awaitSingle
import org.koin.java.KoinJavaComponent.inject
import java.util.logging.Logger

@Command(
    command = "feed",
    description = "Enable or disable the youtube notifications on this channel"
)
class FeedCommand : CommandHandler() {

    private val logger = Logger.getLogger(FeedCommand::class.toString())
    private val creator: GuildPreferenceCreator by inject(GuildPreferenceCreator::class.java)
    private val deleter: GuildPreferenceDeleter by inject(GuildPreferenceDeleter::class.java)
    private val finder: GuildPreferencesFinder by inject(GuildPreferencesFinder::class.java)

    override val commandArguments: Array<CommandArgument<*>> = arrayOf(
        CommandArgument<String>(name = "status", type = String::class, required = true)
    )

    companion object {
        private const val ENABLE_STATUS = "on"
        private const val DISABLE_STATUS = "off"
    }

    override suspend fun handle(message: Message, args: Map<String, Any?>) {
        val guildId = message.guildId.get().asString()
        val channel = message.channel.awaitSingle()
        val status = args["status"] as String

        if (status !in arrayOf(ENABLE_STATUS, DISABLE_STATUS)) {
            channel.createMessage("El argumento $status es invalido, debe ser \"$ENABLE_STATUS\" para activar o \"$DISABLE_STATUS\" para desactivar").awaitSingle()
            return
        }

        val feedChannelId = finder.find(guildId).let {
            if (it.isLeft()) {
                logger.info(it.leftOrNull()!!.message)
                return
            }

            it.getOrNull()!!.preferences[GuildPreferences.GuildPreference.FeedChannelId]
        }

        if (feedChannelId == channel.id.asString() && status == ENABLE_STATUS) {
            channel.createMessage("Las notificaciones de youtube ya estan activadas para este canal").awaitSingle()
            return
        }

        if (feedChannelId != channel.id.asString() && status == ENABLE_STATUS) {
            creator.create(guildId, GuildPreferences.GuildPreference.FeedChannelId, channel.id.asString())
            channel.createMessage("Se han activado las notificaciones de youtube en este canal").awaitSingle()
            return
        }

        if (feedChannelId == channel.id.asString() && status == DISABLE_STATUS) {
            deleter.delete(guildId, GuildPreferences.GuildPreference.FeedChannelId)
            channel.createMessage("Se han desactivado las notificaciones de youtube en este canal").awaitSingle()
            return
        }

        if (feedChannelId != channel.id.asString() && status == DISABLE_STATUS) {
            channel.createMessage("Las notificaciones de youtube no estaban activadas para este canal").awaitSingle()
        }
    }
}
