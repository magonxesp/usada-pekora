package com.usadapekora.bot.application.guild.update

data class ProvidedGuildUpdateRequest(
    val provider: String,
    val providerId: String,
    val values: NewValues
) {
    data class NewValues(
        val name: String? = null,
        val iconUrl: String? = null,
    )
}
