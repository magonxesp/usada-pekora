package com.usadapekora.shared.application.user

import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.application.user.create.UserCreateRequest
import com.usadapekora.shared.application.user.create.UserCreator
import com.usadapekora.shared.domain.UserMother
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertTrue

class UserCreatorTest {

    @Test
    fun `should create user`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>(relaxed = true)
        val creator = UserCreator(repository)

        every { repository.find(user.id) } returns UserException.NotFound().left()

        val request = UserCreateRequest(
            id = user.id.value,
            name = user.name.value,
            avatar = user.avatar?.value,
            providerId = user.providerId.value,
            provider = user.provider.value
        )
        val result = creator.create(request)

        assertTrue(result.isRight())

        verify { repository.find(user.id) }
        verify { repository.save(user) }
    }

    @Test
    fun `should not create an existing user`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>(relaxed = true)
        val creator = UserCreator(repository)

        every { repository.find(user.id) } returns user.right()

        val request = UserCreateRequest(
            id = user.id.value,
            name = user.name.value,
            avatar = user.avatar?.value,
            providerId = user.providerId.value,
            provider = user.provider.value
        )
        val result = creator.create(request)

        assertTrue(result.leftOrNull() is UserException.AlreadyExists)

        verify { repository.find(user.id) }
        verify(inverse = true) { repository.save(user) }
    }

}
