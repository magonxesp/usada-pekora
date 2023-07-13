package com.usadapekora.shared.application.user

import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.application.user.create.UserSessionCreateRequest
import com.usadapekora.shared.application.user.create.UserSessionCreator
import com.usadapekora.shared.domain.UserSessionMother
import com.usadapekora.shared.domain.user.UserSessionError
import com.usadapekora.shared.domain.user.UserSessionRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class UserSessionCreatorTest {

    private val userSessionRepository = mockk<UserSessionRepository>()
    private val userSessionCreator = UserSessionCreator(userSessionRepository)

    @BeforeTest
    fun resetMocks() = clearAllMocks()

    @Test
    fun `it should create a user session`() {
        val session = UserSessionMother.create()

        every { userSessionRepository.find(session.id) } returns UserSessionError.NotFound().left()
        every { userSessionRepository.save(session) } returns Unit.right()

        val result = userSessionCreator.create(UserSessionCreateRequest(
            id = session.id(),
            userId = session.userId.value,
            state = session.state.name,
            expiresAt = session.expiresAt.value,
            lastActiveAt = session.lastActiveAt.value,
            device = session.device.value
        ))

        verify { userSessionRepository.find(session.id) }
        verify { userSessionRepository.save(session) }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

    @Test
    fun `it should create a not existing user session`() {
        val session = UserSessionMother.create()

        every { userSessionRepository.find(session.id) } returns session.right()
        every { userSessionRepository.save(session) } returns Unit.right()

        val result = userSessionCreator.create(UserSessionCreateRequest(
            id = session.id(),
            userId = session.userId.value,
            state = session.state.name,
            expiresAt = session.expiresAt.value,
            lastActiveAt = session.lastActiveAt.value,
            device = session.device.value
        ))

        verify { userSessionRepository.find(session.id) }
        verify(inverse = true) { userSessionRepository.save(session) }

        assertIs<UserSessionError.AlreadyExists>(result.leftOrNull())
    }

}
