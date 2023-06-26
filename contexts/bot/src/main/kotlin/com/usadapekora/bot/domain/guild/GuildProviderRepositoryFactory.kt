package com.usadapekora.bot.domain.guild

import arrow.core.Either

interface GuildProviderRepositoryFactory {
    fun getInstance(provider: GuildProvider, token: String): Either<GuildProviderError.NotFound, GuildProviderRepository>
}
