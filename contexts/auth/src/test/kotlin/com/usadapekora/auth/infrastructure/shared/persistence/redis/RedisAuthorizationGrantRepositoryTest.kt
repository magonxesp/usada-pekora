package com.usadapekora.auth.infrastructure.shared.persistence.redis

import com.usadapekora.auth.domain.AuthorizationGrantMother
import kotlin.test.Test
import kotlin.test.assertEquals

class RedisAuthorizationGrantRepositoryTest {

    @Test
    fun `it should save`() {
        val repository = RedisAuthorizationGrantRepository()
        val authorizationGrant = AuthorizationGrantMother.create()

        repository.save(authorizationGrant)
        val saved = repository.find(authorizationGrant.code)

        assertEquals(authorizationGrant, saved.getOrNull())
    }

}
