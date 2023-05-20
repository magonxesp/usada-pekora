package com.usadapekora.bot.domain

import com.usadapekora.shared.domain.Entity

interface ObjectMother<E : Entity> {
    fun random(): E
}
