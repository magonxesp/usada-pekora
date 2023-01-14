package com.usadapekora.context.domain.trigger.matcher

import com.usadapekora.context.domain.trigger.Trigger

class TriggerInMatcher : TriggerInputMatcher {
    override fun match(input: String, trigger: Trigger): Boolean
        = input.lowercase().contains(trigger.input.value.lowercase())
}
