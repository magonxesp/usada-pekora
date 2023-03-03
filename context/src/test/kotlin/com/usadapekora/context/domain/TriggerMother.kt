package com.usadapekora.context.domain

import com.usadapekora.context.domain.trigger.Trigger

object TriggerMother : ObjectMother<Trigger> {
    fun create(
        id: String? = null,
        title: String? = null,
        input: String? = null,
        compare: String? = null,
        outputText: String? = null,
        discordGuildId: String? = null
    ) = Trigger.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        title = title ?: Random.instance().chiquito.terms(),
        input = input ?: Random.instance().chiquito.expressions(),
        compare = compare ?: Trigger.TriggerCompare.values().random().toString(),
        outputText = outputText ?: Random.instance().chiquito.sentences(),
        discordGuildId = discordGuildId ?: java.util.Random().nextLong().toString()
    )

    override fun random(): Trigger = create()
}
