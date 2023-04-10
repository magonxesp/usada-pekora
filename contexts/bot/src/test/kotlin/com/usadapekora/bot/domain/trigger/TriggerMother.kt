package com.usadapekora.bot.domain.trigger

import com.usadapekora.bot.domain.ObjectMother
import com.usadapekora.bot.domain.Random
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.trigger.response.text.TriggerTextMother

object TriggerMother : ObjectMother<Trigger> {
    fun create(
        id: String? = null,
        title: String? = null,
        input: String? = null,
        compare: String? = null,
        responseTextId: String? = null,
        responseAudioId: String? = null,
        responseAudioProvider: TriggerAudioResponseProvider? = null,
        guildId: String? = null
    ) = Trigger.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        title = title ?: Random.instance().chiquito.terms(),
        input = input ?: Random.instance().chiquito.expressions(),
        compare = compare ?: Trigger.TriggerCompare.values().random().toString(),
        responseTextId = responseTextId ?: Random.instance().random.nextUUID(),
        responseAudioId = responseAudioId ?: Random.instance().random.nextUUID(),
        responseAudioProvider = responseAudioProvider?.value ?: Random.instance().random.nextEnum(TriggerAudioResponseProvider::class.java).value,
        discordGuildId = guildId ?: java.util.Random().nextLong().toString()
    )

    override fun random(): Trigger = create()
}
