package com.usadapekora.context.domain.guild

import com.usadapekora.context.domain.shared.Entity

data class GuildPreferences(
    val guildId: String,
    val preferences: MutableMap<GuildPreference, String> = mutableMapOf()
): Entity() {
    enum class GuildPreference(val value: String) {
        FeedChannelId("feed_chanel_id")
    }

    override fun id(): String = guildId
}
