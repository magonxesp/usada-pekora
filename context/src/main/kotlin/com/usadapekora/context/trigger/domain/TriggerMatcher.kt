package com.usadapekora.context.trigger.domain

import com.usadapekora.context.trigger.domain.matcher.TriggerInMatcher
import com.usadapekora.context.trigger.domain.matcher.TriggerInputMatcher
import com.usadapekora.context.trigger.domain.matcher.TriggerPatternMatcher

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
