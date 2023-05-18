package com.usadapekora.auth.domain

interface SignatureVerifier {
    fun verify(token: String): Boolean
}
