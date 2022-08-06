package es.magonxesp.pekorabot.modules.trigger.domain

import es.magonxesp.pekorabot.modules.shared.domain.AggregateRoot

class Trigger(
    val input: String,
    val compare: TriggerCompare,
    val outputText: String?,
    val outputSound: String?
) : AggregateRoot() {
    enum class TriggerCompare(val value: String) {
        In("in"),
        Pattern("pattern");

        companion object {
            fun fromValue(value: String): TriggerCompare {
                val instance = values().find {
                    it.value == value
                } ?: throw TriggerException.InvalidValue()

                return instance
            }
        }
    }

    companion object {
        fun fromPrimitives(
            input: String,
            compare: String,
            outputText: String?,
            outputSound: String?
        ) = Trigger(
            input = input,
            compare = TriggerCompare.fromValue(compare),
            outputText = outputText,
            outputSound = outputSound
        )
    }
}
