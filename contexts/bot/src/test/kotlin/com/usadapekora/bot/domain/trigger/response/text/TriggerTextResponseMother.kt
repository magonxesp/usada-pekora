package com.usadapekora.bot.domain.trigger.response.text

import com.usadapekora.bot.domain.Random
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.shared.domain.ObjectMother

object TriggerTextResponseMother : ObjectMother<TriggerTextResponse> {
    fun create(
        id: String? = null,
        content: String? = null,
        type: String? = null
    ) = TriggerTextResponse.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        content = content ?: Random.instance().chiquito.jokes(),
        type = type ?: "text"
    )

    override fun random(): TriggerTextResponse = create()
}
