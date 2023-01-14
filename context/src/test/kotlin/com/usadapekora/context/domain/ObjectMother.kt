package com.usadapekora.context.domain

import com.usadapekora.context.domain.shared.Entity

interface ObjectMother<E : Entity> {
    fun random(): E
}
