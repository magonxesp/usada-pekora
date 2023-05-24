package com.usadapekora.auth.infrastructure.oauth.persistence.redis

import com.usadapekora.auth.domain.OAuthAuthorizationGrantMother
import kotlin.test.Test
import kotlin.test.assertEquals

class RedisOAuthAuthorizationGrantRepositoryTest {

    @Test
    fun `it should save`() {
        val repository = RedisOAuthAuthorizationGrantRepository()
        val authorizationGrant = OAuthAuthorizationGrantMother.create()

        repository.save(authorizationGrant)
        val saved = repository.find(authorizationGrant.code)

        assertEquals(authorizationGrant, saved.getOrNull())
    }

}
