package com.usadapekora.context.application.user

import com.usadapekora.context.application.user.UserCreator
import com.usadapekora.context.domain.UserMother
import com.usadapekora.context.domain.user.UserRepository
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class UserCreatorTest {

    @Test
    fun `should create user`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>(relaxed = true)
        val creator = UserCreator(repository)

        val created = creator.create(user.id, user.discordId)

        verify { repository.save(user) }

        assertEquals(user, created)
    }

}
