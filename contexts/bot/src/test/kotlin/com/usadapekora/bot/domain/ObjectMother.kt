package com.usadapekora.bot.domain

import com.usadapekora.bot.domain.shared.Entity

interface ObjectMother<E : Entity> {
    fun random(): E
}
