package com.usadapekora.context.application.trigger

import com.usadapekora.context.domain.TriggerMother
import com.usadapekora.context.application.trigger.find.TriggerFinder
import com.usadapekora.context.domain.trigger.*
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

}
