package com.usadapekora.auth.domain.jwt

import arrow.core.Either

interface JwkIssuer {
    fun issue(): Either<JwkException, String>
}
