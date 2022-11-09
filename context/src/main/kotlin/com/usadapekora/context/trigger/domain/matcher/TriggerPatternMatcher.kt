package com.usadapekora.context.trigger.domain.matcher

import com.usadapekora.context.trigger.domain.Trigger

class TriggerPatternMatcher : TriggerInputMatcher {
    override fun match(input: String, trigger: Trigger): Boolean
        = Regex(trigger.input).matches(input)
}
