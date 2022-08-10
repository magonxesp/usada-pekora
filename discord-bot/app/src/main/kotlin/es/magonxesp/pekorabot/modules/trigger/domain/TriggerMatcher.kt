package es.magonxesp.pekorabot.modules.trigger.domain

import es.magonxesp.pekorabot.modules.trigger.domain.matcher.TriggerInMatcher
import es.magonxesp.pekorabot.modules.trigger.domain.matcher.TriggerInputMatcher
import es.magonxesp.pekorabot.modules.trigger.domain.matcher.TriggerPatternMatcher

class TriggerMatcher {

    private fun matcher(trigger: Trigger): TriggerInputMatcher = when (trigger.compare) {
        Trigger.TriggerCompare.In -> TriggerInMatcher()
        Trigger.TriggerCompare.Pattern -> TriggerPatternMatcher()
    }

    fun matchInput(input: String, triggers: Array<Trigger>): Trigger? {
        for (trigger in triggers) {
            if (matcher(trigger).match(input, trigger)) {
                return trigger
            }
        }

        return null
    }

}
