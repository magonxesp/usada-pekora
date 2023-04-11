package com.usadapekora.bot.domain.trigger.response.text

import com.usadapekora.bot.domain.ObjectMother
import com.usadapekora.bot.domain.Random
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse

object TriggerTextMother : ObjectMother<TriggerTextResponse> {
    fun create(
        id: String? = null,
        content: String? = null,
        type: String? = null,
        triggerId: String? = null,
        guildId: String? = null
    ) = TriggerTextResponse.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        content = content ?: Random.instance().chiquito.jokes(),
        type = type ?: "text",
        triggerId = triggerId ?: Random.instance().random.nextUUID(),
        discordGuildId = guildId ?: Random.instance().random.nextUUID()
    )

    override fun random(): TriggerTextResponse = create()
}
