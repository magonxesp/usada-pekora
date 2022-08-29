package es.magonxesp.pekorabot.modules.guild.domain

data class GuildPreferences(
    val guildId: String,
    val preferences: MutableMap<GuildPreference, Any> = mutableMapOf()
) {
    enum class GuildPreference(val value: String) {
        FeedChannelId("feed_chanel_id")
    }
}
