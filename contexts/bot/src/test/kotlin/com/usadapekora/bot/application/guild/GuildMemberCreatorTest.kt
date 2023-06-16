package com.usadapekora.bot.application.guild

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.guild.create.GuildMemberCreateRequest
import com.usadapekora.bot.application.guild.create.GuildMemberCreator
import com.usadapekora.bot.domain.guild.GuildMemberError
import com.usadapekora.bot.domain.guild.GuildMemberMother
import com.usadapekora.bot.domain.guild.GuildMemberRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class GuildMemberCreatorTest {

    private val repository = mockk<GuildMemberRepository>()
    private val creator = GuildMemberCreator(repository)

    @BeforeTest
    fun clearMocks() = clearAllMocks()

    @Test
    fun `it should create guild member`() {
        val member = GuildMemberMother.create()

        every { repository.find(member.user, member.guild) } returns GuildMemberError.NotFound().left()
        every { repository.save(member) } returns Unit.right()

        val result = creator.create(GuildMemberCreateRequest(userId = member.user.value, guildId = member.guild.value))

        verify { repository.find(member.user, member.guild) }
        verify { repository.save(member) }

        assertIs<Unit>(result.getOrNull())
    }

    @Test
    fun `it should not create existing guild member`() {
        val member = GuildMemberMother.create()

        every { repository.find(member.user, member.guild) } returns member.right()
        every { repository.save(member) } returns Unit.right()

        val result = creator.create(GuildMemberCreateRequest(userId = member.user.value, guildId = member.guild.value))

        verify { repository.find(member.user, member.guild) }
        verify(inverse = true) { repository.save(member) }

        assertIs<GuildMemberError.AlreadyExists>(result.leftOrNull())
    }

}
