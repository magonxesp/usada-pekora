package com.usadapekora.shared.domain

import com.usadapekora.shared.domain.common.Entity

interface ObjectMother<E : Entity> {
    fun random(): E
}
