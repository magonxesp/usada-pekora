package com.usadapekora.bot.application.guild

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.guild.update.ProvidedGuildUpdateRequest
import com.usadapekora.bot.application.guild.update.ProvidedGuildUpdater
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.GuildError
import com.usadapekora.bot.domain.guild.GuildMother
import com.usadapekora.bot.domain.guild.GuildRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class ProvidedGuildUpdaterTest {

    private val repository = mockk<GuildRepository>()
    private val updater = ProvidedGuildUpdater(repository)

    @BeforeTest
    fun beforeTest() = clearAllMocks()

    @Test
    fun `it should update a provided guild`() {
        val guild = GuildMother.create()
        val guildUpdated = guild.copy(
            name = Guild.GuildName("new name"),
            iconUrl = Guild.GuildIconUrl("new icon url")
        )
        val request = ProvidedGuildUpdateRequest(
            provider = guild.provider.value,
            providerId = guild.providerId.value,
            values = ProvidedGuildUpdateRequest.NewValues(
                name = guildUpdated.name.value,
                iconUrl = guildUpdated.iconUrl.value
            )
        )

        every { repository.findByProvider(guild.providerId, guild.provider) } returns guild.right()
        every { repository.save(guildUpdated) } returns Unit.right()

        val result = updater.update(request)

        verify { repository.findByProvider(guild.providerId, guild.provider) }
        verify { repository.save(guildUpdated) }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

    @Test
    fun `it should not update not existing provided guild`() {
        val guild = GuildMother.create()
        val guildUpdated = guild.copy(
            name = Guild.GuildName("new name"),
            iconUrl = Guild.GuildIconUrl("new icon url")
        )
        val request = ProvidedGuildUpdateRequest(
            provider = guild.provider.value,
            providerId = guild.providerId.value,
            values = ProvidedGuildUpdateRequest.NewValues(
                name = guildUpdated.name.value,
                iconUrl = guildUpdated.iconUrl.value
            )
        )

        every { repository.findByProvider(guild.providerId, guild.provider) } returns GuildError.NotFound().left()
        every { repository.save(guildUpdated) } returns Unit.right()

        val result = updater.update(request)

        verify { repository.findByProvider(guild.providerId, guild.provider) }
        verify(inverse = true) { repository.save(guildUpdated) }

        assertIs<GuildError.NotFound>(result.leftOrNull())
    }

}
