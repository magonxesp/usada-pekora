package com.usadapekora.context.domain.trigger

import com.usadapekora.context.domain.trigger.matcher.TriggerInMatcher
import com.usadapekora.context.domain.trigger.matcher.TriggerInputMatcher
import com.usadapekora.context.domain.trigger.matcher.TriggerPatternMatcher

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
