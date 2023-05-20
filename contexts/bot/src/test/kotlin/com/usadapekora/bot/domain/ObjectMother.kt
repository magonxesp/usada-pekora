package com.usadapekora.bot.domain

import com.usadapekora.shared.domain.common.Entity

interface ObjectMother<E : Entity> {
    fun random(): E
}
