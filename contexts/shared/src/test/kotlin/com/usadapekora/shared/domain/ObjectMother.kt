package com.usadapekora.shared.domain

interface ObjectMother<E : Entity> {
    fun random(): E
}
