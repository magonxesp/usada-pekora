package com.usadapekora.shared.domain

import java.util.UUID

class IdCreator {
    fun create(): String = UUID.randomUUID().toString()
}
