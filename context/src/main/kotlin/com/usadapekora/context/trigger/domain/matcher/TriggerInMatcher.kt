package com.usadapekora.context.trigger.domain.matcher

import com.usadapekora.context.trigger.domain.Trigger

class TriggerInMatcher : TriggerInputMatcher {
    override fun match(input: String, trigger: Trigger): Boolean
        = input.lowercase().contains(trigger.input.lowercase())
}
