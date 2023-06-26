package com.usadapekora.bot.infraestructure.guild.koin

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.domain.guild.GuildProviderError
import com.usadapekora.bot.domain.guild.GuildProviderRepository
import com.usadapekora.bot.domain.guild.GuildProviderRepositoryFactory
import com.usadapekora.bot.infraestructure.guild.persistence.discord.DiscordGuildProviderRepository
import com.usadapekora.shared.serviceContainer
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

class KoinGuildProviderRepositoryFactory : GuildProviderRepositoryFactory {
    private val providers = mapOf<GuildProvider, KClass<*>>(
        GuildProvider.DISCORD to DiscordGuildProviderRepository::class
    )

    override fun getInstance(provider: GuildProvider, token: String): Either<GuildProviderError.NotFound, GuildProviderRepository> {
        val providerClass = providers[provider] ?: return GuildProviderError.NotFound("The ${provider.value} guild provider is not available").left()
        return serviceContainer().get<GuildProviderRepository>(
            clazz = providerClass,
            parameters = { parametersOf(token) }
        ).right()
    }
}
