package com.usadapekora.bot.domain.guild

import com.usadapekora.shared.domain.common.Entity

data class GuildPreferences(
    val guildId: String,
    val preferences: MutableMap<GuildPreference, String> = mutableMapOf()
): Entity() {
    enum class GuildPreference(val value: String) {
        FeedChannelId("feed_chanel_id")
    }

    override fun id(): String = guildId
}
