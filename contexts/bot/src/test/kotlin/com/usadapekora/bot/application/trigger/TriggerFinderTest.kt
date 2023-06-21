package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.find.TriggerFinder
import com.usadapekora.bot.application.trigger.find.TriggerResponse
import com.usadapekora.bot.application.trigger.find.TriggersResponse
import com.usadapekora.bot.domain.trigger.*
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TriggerFinderTest {

    @Test
    fun `should find trigger that matches using the contains comparator`() {
        val expected = TriggerMother.create(
            input = "pekora",
            compare = Trigger.TriggerCompare.In.value
        )

        val repository = mockk<TriggerRepository>()
        val finder = TriggerFinder(repository, TriggerMatcher())

        every { repository.findByDiscordServer(expected.discordGuildId) } returns arrayOf(expected)

        val actual = finder.findByInput("It's me pekora", expected.discordGuildId.value)

        assertEquals(TriggerResponse.fromEntity(expected), actual)
    }

    @Test
    fun `should not find trigger that matches using the contains comparator`() {
        val expected = TriggerMother.create(
            compare = Trigger.TriggerCompare.In.value
        )

        val repository = mockk<TriggerRepository>()
        val finder = TriggerFinder(repository, TriggerMatcher())

        every { repository.findByDiscordServer(expected.discordGuildId) } returns arrayOf(expected)

        assertThrows<TriggerException.NotFound> {
            finder.findByInput("It's me pekora", expected.discordGuildId.value)
        }
    }

    @Test
    fun `should find trigger that matches using the pattern comparator`() {
        val expected = TriggerMother.create(
            input = "( +)(([HhJj]+)([Aa]+)){3,}( +)?|^(([HhJj]+)([Aa]+)){3,}\$",
            compare = Trigger.TriggerCompare.Pattern.value
        )

        val repository = mockk<TriggerRepository>()
        val finder = TriggerFinder(repository, TriggerMatcher())

        every { repository.findByDiscordServer(expected.discordGuildId) } returns arrayOf(expected)

        val actual = finder.findByInput("jajajajajajaja", expected.discordGuildId.value)

        assertEquals(TriggerResponse.fromEntity(expected), actual)
    }


    @Test
    fun `should not find trigger that matches using the pattern comparator`() {
        val expected = TriggerMother.create(
            input = "(.*)alemana?(.*)loc[oa](.*)",
            compare = Trigger.TriggerCompare.Pattern.value
        )

        val repository = mockk<TriggerRepository>()
        val finder = TriggerFinder(repository, TriggerMatcher())

        every { repository.findByDiscordServer(expected.discordGuildId) } returns arrayOf(expected)

        assertThrows<TriggerException.NotFound> {
            finder.findByInput("jajajajajajaj", expected.discordGuildId.value)
        }
    }

    @Test
    fun `should find trigger by id`() {
        val expected = TriggerMother.create()

        val repository = mockk<TriggerRepository>()
        val finder = TriggerFinder(repository, TriggerMatcher())

        every { repository.find(expected.id) } returns expected.right()

        val actual = finder.find(expected.id.value)

        assertTrue(actual.isRight())
        assertEquals(TriggerResponse.fromEntity(expected), actual.getOrNull())
    }

    @Test
    fun `should not find trigger by id`() {
        val expected = TriggerMother.create()

        val repository = mockk<TriggerRepository>()
        val finder = TriggerFinder(repository, TriggerMatcher())

        every { repository.find(expected.id) } returns TriggerException.NotFound().left()

        val result = finder.find(expected.id.value)

        assertTrue(result.leftOrNull() is TriggerException.NotFound)
    }

    @Test
    fun `should find trigger by discord server id`() {
        val trigger = TriggerMother.create()

        val repository = mockk<TriggerRepository>()
        val finder = TriggerFinder(repository, TriggerMatcher())

        every { repository.findByDiscordServer(trigger.discordGuildId) } returns arrayOf(trigger)

        val response = finder.findByDiscordServer(trigger.discordGuildId.value)
        assertContentEquals(response.triggers, TriggersResponse.fromArray(arrayOf(trigger)).triggers)
    }

    @Test
    fun `should not find trigger by discord server id`() {
        val expected = TriggerMother.create()

        val repository = mockk<TriggerRepository>()
        val finder = TriggerFinder(repository, TriggerMatcher())

        every { repository.findByDiscordServer(expected.discordGuildId) } returns arrayOf()

        val response = finder.findByDiscordServer(expected.discordGuildId.value)
        assertContentEquals(response.triggers, TriggersResponse(arrayOf()).triggers)
    }
}
