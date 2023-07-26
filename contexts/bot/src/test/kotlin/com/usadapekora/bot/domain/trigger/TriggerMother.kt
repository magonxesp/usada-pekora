package com.usadapekora.bot.domain.trigger

import com.usadapekora.bot.domain.Random
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseProvider
import com.usadapekora.shared.domain.ObjectMother

object TriggerMother : ObjectMother<Trigger> {
    fun create(
        id: String? = null,
        title: String? = null,
        input: String? = null,
        compare: String? = null,
        kind: String? = null,
        responseTextId: String? = null,
        responseAudioId: String? = null,
        responseAudioProvider: TriggerAudioResponseProvider? = null,
        guildId: String? = null,
        overrides: String? = null
    ) = Trigger.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        title = title ?: Random.instance().chiquito.terms(),
        input = input ?: Random.instance().chiquito.expressions(),
        compare = compare ?: Trigger.TriggerCompare.values().random().toString(),
        kind = kind ?: Random.instance().random.nextEnum(TriggerKind::class.java).value,
        responseTextId = responseTextId ?: Random.instance().random.nextUUID(),
        responseAudioId = responseAudioId ?: Random.instance().random.nextUUID(),
        responseAudioProvider = responseAudioProvider?.value ?: Random.instance().random.nextEnum(
            TriggerAudioResponseProvider::class.java).value,
        guildId = guildId ?: java.util.Random().nextLong().toString(),
        overrides = overrides
    )

    override fun random(): Trigger = create()
}
