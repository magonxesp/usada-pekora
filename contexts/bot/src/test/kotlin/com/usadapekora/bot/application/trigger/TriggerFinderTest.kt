package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.find.TriggerFinder
import com.usadapekora.bot.application.trigger.find.TriggerResponse
import com.usadapekora.bot.application.trigger.find.TriggersResponse
import com.usadapekora.bot.domain.trigger.*
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

class TriggerFinderTest {

    private val repository = mockk<TriggerRepository>()
    private val finder = TriggerFinder(repository, TriggerMatcher())

    @BeforeTest
    fun resetMocks() = clearAllMocks()

    @Test
    fun `it should find trigger that matches using the contains comparator`() {
        val expected = TriggerMother.create(
            input = "pekora",
            compare = Trigger.TriggerCompare.In.value
        )

        every { repository.findByGuild(expected.guildId!!) } returns arrayOf(expected)

        val actual = finder.findByInput("It's me pekora", expected.guildId!!.value)

        verify { repository.findByGuild(expected.guildId!!) }

        assertEquals(TriggerResponse.fromEntity(expected), actual)
    }

    @Test
    fun `it should not find trigger that matches using the contains comparator`() {
        val expected = TriggerMother.create(
            compare = Trigger.TriggerCompare.In.value
        )

        every { repository.findByGuild(expected.guildId!!) } returns arrayOf(expected)

        assertThrows<TriggerException.NotFound> {
            finder.findByInput("It's me pekora", expected.guildId!!.value)
        }

        verify { repository.findByGuild(expected.guildId!!) }
    }

    @Test
    fun `it should find trigger that matches using the pattern comparator`() {
        val expected = TriggerMother.create(
            input = "( +)(([HhJj]+)([Aa]+)){3,}( +)?|^(([HhJj]+)([Aa]+)){3,}\$",
            compare = Trigger.TriggerCompare.Pattern.value
        )

        every { repository.findByGuild(expected.guildId!!) } returns arrayOf(expected)

        val actual = finder.findByInput("jajajajajajaja", expected.guildId!!.value)

        verify { repository.findByGuild(expected.guildId!!) }

        assertEquals(TriggerResponse.fromEntity(expected), actual)
    }


    @Test
    fun `it should not find trigger that matches using the pattern comparator`() {
        val expected = TriggerMother.create(
            input = "(.*)alemana?(.*)loc[oa](.*)",
            compare = Trigger.TriggerCompare.Pattern.value
        )

        every { repository.findByGuild(expected.guildId!!) } returns arrayOf(expected)

        assertThrows<TriggerException.NotFound> {
            finder.findByInput("jajajajajajaj", expected.guildId!!.value)
        }

        verify { repository.findByGuild(expected.guildId!!) }
    }

    @Test
    fun `it should find trigger by id`() {
        val expected = TriggerMother.create()

        every { repository.find(expected.id) } returns expected.right()

        val actual = finder.find(expected.id.value)

        verify { repository.find(expected.id) }

        assertTrue(actual.isRight())
        assertEquals(TriggerResponse.fromEntity(expected), actual.getOrNull())
    }

    @Test
    fun `it should not find trigger by id`() {
        val expected = TriggerMother.create()

        every { repository.find(expected.id) } returns TriggerException.NotFound().left()

        val result = finder.find(expected.id.value)

        verify { repository.find(expected.id) }

        assertTrue(result.leftOrNull() is TriggerException.NotFound)
    }

    @Test
    fun `it should find trigger by discord server id`() {
        val trigger = TriggerMother.create()

        every { repository.findByGuild(trigger.guildId!!) } returns arrayOf(trigger)

        val response = finder.findByGuild(trigger.guildId!!.value)

        verify { repository.findByGuild(trigger.guildId!!) }

        assertContentEquals(response.triggers, TriggersResponse.fromArray(arrayOf(trigger)).triggers)
    }

    @Test
    fun `it should not find trigger by discord server id`() {
        val expected = TriggerMother.create()

        every { repository.findByGuild(expected.guildId!!) } returns arrayOf()

        val response = finder.findByGuild(expected.guildId!!.value)

        verify { repository.findByGuild(expected.guildId!!) }

        assertContentEquals(arrayOf(), response.triggers)
    }
}
