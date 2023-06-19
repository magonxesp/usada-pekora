package com.usadapekora.shared.domain

import com.usadapekora.shared.domain.bus.event.EventProcessed
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

object EventProcessedMother : ObjectMother<EventProcessed> {

    fun create(
        id: String? = null,
        name: String? = null,
        consumedBy: String? = null,
        consumedOn: Instant? = null,
        timeElapsedMilliseconds: Long? = null
    ) = EventProcessed.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        name = name ?: Random.instance().internet.slug(),
        consumedBy = consumedBy ?: Random.instance().overwatch.heroes(),
        consumedOn = consumedOn ?: Clock.System.now(),
        timeElapsedMilliseconds = timeElapsedMilliseconds ?: Random.instance().random.nextLong()
    )

    override fun random() = create()
}
