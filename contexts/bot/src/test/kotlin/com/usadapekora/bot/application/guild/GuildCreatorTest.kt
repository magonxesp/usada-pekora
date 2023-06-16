package com.usadapekora.bot.application.guild

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.guild.find.GuildCreateRequest
import com.usadapekora.bot.application.guild.find.GuildCreator
import com.usadapekora.bot.domain.GuildMother
import com.usadapekora.bot.domain.guild.GuildError
import com.usadapekora.bot.domain.guild.GuildRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class GuildCreatorTest {

    private val repository = mockk<GuildRepository>()
    private val creator = GuildCreator(repository)

    @BeforeTest
    fun cleanMocks() = clearAllMocks()

    @Test
    fun `it should create a guild`() {
        val guild = GuildMother.create()

        every { repository.find(guild.id) } returns GuildError.NotFound().left()
        every { repository.findByProvider(guild.providerId, guild.provider) } returns GuildError.NotFound().left()
        every { repository.save(guild) } returns Unit.right()

        val result = creator.create(
            GuildCreateRequest(
                id = guild.id.value,
                name = guild.name.value,
                iconUrl = guild.iconUrl.value,
                providerId = guild.providerId.value,
                provider = guild.provider.value
            )
        )

        verify { repository.find(guild.id) }
        verify { repository.save(guild) }

        assertIs<Unit>(result.getOrNull())
    }

    @Test
    fun `it not should create a existing guild`() {
        val guild = GuildMother.create()

        every { repository.find(guild.id) } returns guild.right()
        every { repository.findByProvider(guild.providerId, guild.provider) } returns GuildError.NotFound().left()
        every { repository.save(guild) } returns Unit.right()

        val result = creator.create(
            GuildCreateRequest(
                id = guild.id.value,
                name = guild.name.value,
                iconUrl = guild.iconUrl.value,
                providerId = guild.providerId.value,
                provider = guild.provider.value
            )
        )

        verify { repository.find(guild.id) }
        verify(inverse = true) { repository.findByProvider(guild.providerId, guild.provider) }
        verify(inverse = true) { repository.save(guild) }

        assertIs<GuildError.AlreadyExists>(result.leftOrNull())
    }

    @Test
    fun `it not should create a existing guild that contains the same provider id`() {
        val guild = GuildMother.create()

        every { repository.find(guild.id) } returns GuildError.NotFound().left()
        every { repository.findByProvider(guild.providerId, guild.provider) } returns guild.right()
        every { repository.save(guild) } returns Unit.right()

        val result = creator.create(
            GuildCreateRequest(
                id = guild.id.value,
                name = guild.name.value,
                iconUrl = guild.iconUrl.value,
                providerId = guild.providerId.value,
                provider = guild.provider.value
            )
        )

        verify { repository.find(guild.id) }
        verify { repository.findByProvider(guild.providerId, guild.provider) }
        verify(inverse = true) { repository.save(guild) }

        assertIs<GuildError.AlreadyExists>(result.leftOrNull())
    }

}
