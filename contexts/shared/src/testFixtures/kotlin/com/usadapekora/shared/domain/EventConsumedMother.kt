package com.usadapekora.shared.domain

import com.usadapekora.shared.domain.bus.event.EventConsumed

object EventConsumedMother : ObjectMother<EventConsumed> {

    fun create(
        id: String? = null,
        consumedBy: String? = null
    ) = EventConsumed.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        consumedBy = consumedBy ?: Random.instance().overwatch.heroes()
    )

    override fun random() = create()
}
