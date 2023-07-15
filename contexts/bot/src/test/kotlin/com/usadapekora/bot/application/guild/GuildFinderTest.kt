package com.usadapekora.bot.application.guild

import com.usadapekora.bot.application.guild.find.GuildFinder
import com.usadapekora.bot.application.guild.find.GuildResponse
import com.usadapekora.bot.domain.guild.GuildMother
import com.usadapekora.bot.domain.guild.GuildRepository
import com.usadapekora.shared.domain.UserMother
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertContentEquals

class GuildFinderTest {

    @Test
    fun `it should find guilds by user`() {
        val guilds = GuildMother.createList().toTypedArray()
        val user = UserMother.create()

        val repository = mockk<GuildRepository>()
        val finder = GuildFinder(repository)

        every { repository.findByUserId(user.id) } returns guilds

        val result = finder.findUserGuilds(user.id)

        verify { repository.findByUserId(user.id) }

        assertContentEquals(guilds.map { GuildResponse.fromEntity(it) }.toTypedArray(), result.guilds)
    }

}
