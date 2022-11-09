package com.usadapekora.context.guild.domain

data class GuildPreferences(
    val guildId: String,
    val preferences: MutableMap<GuildPreference, String> = mutableMapOf()
) {
    enum class GuildPreference(val value: String) {
        FeedChannelId("feed_chanel_id")
    }
}
