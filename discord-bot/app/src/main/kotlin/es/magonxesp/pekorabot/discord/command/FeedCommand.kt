package es.magonxesp.pekorabot.discord.command

import discord4j.core.`object`.entity.Message
import es.magonxesp.pekorabot.discord.shared.CommandHandler
import es.magonxesp.pekorabot.discord.shared.annotation.Command
import es.magonxesp.pekorabot.discord.shared.CommandArgument
import es.magonxesp.pekorabot.guildPreferenceCreator
import es.magonxesp.pekorabot.guildPreferenceDeleter
import es.magonxesp.pekorabot.guildPreferenceFinder
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferences
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesException
import kotlinx.coroutines.reactor.awaitSingle
import java.util.logging.Logger

@Command(
    command = "feed",
    description = "Enable or disable the youtube notifications on this channel"
)
class FeedCommand : CommandHandler() {

    private val logger = Logger.getLogger(FeedCommand::class.toString())

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
        var feedChannelId: Long? = null
        val status = args["status"] as String

        if (status !in arrayOf(ENABLE_STATUS, DISABLE_STATUS)) {
            channel.createMessage("El argumento $status es invalido, debe ser \"$ENABLE_STATUS\" para activar o \"$DISABLE_STATUS\" para desactivar").awaitSingle()
            return
        }

        try {
            val preferences = guildPreferenceFinder().find(guildId)
            feedChannelId = preferences.preferences[GuildPreferences.GuildPreference.FeedChannelId] as Long?
        } catch (exception: GuildPreferencesException.NotFound) {
            logger.info(exception.message)
        }

        if (feedChannelId == channel.id.asLong() && status == ENABLE_STATUS) {
            channel.createMessage("Las notificaciones de youtube ya estan activadas para este canal").awaitSingle()
            return
        }

        if (feedChannelId != channel.id.asLong() && status == ENABLE_STATUS) {
            guildPreferenceCreator().create(guildId, GuildPreferences.GuildPreference.FeedChannelId, channel.id.asLong())
            channel.createMessage("Se han activado las notificaciones de youtube este canal").awaitSingle()
            return
        }

        if (feedChannelId == channel.id.asLong() && status == DISABLE_STATUS) {
            guildPreferenceDeleter().delete(guildId, GuildPreferences.GuildPreference.FeedChannelId)
            channel.createMessage("Se han desactivado las notificaciones de youtube este canal").awaitSingle()
            return
        }

        if (feedChannelId != channel.id.asLong() && status == DISABLE_STATUS) {
            channel.createMessage("Las notificaciones de youtube no estaban activadas para este canal").awaitSingle()
        }
    }
}
