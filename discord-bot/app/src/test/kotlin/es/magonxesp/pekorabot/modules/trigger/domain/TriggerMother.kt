package es.magonxesp.pekorabot.modules.trigger.domain

import es.magonxesp.pekorabot.modules.shared.domain.Random

class TriggerMother {
    companion object {
        fun create(
            id: String? = null,
            input: String? = null,
            compare: String? = null,
            outputText: String? = null,
            outputSound: String? = null
        ) = Trigger.fromPrimitives(
            id = id ?: Random.instance().random.nextUUID(),
            input = input ?: Random.instance().chiquito.expressions(),
            compare = compare ?: Trigger.TriggerCompare.values().random().toString(),
            outputText = outputText ?: Random.instance().chiquito.sentences(),
            outputSound = outputSound
        )
    }
}
