package com.usadapekora.context.domain.trigger.matcher

import com.usadapekora.context.domain.trigger.Trigger

class TriggerPatternMatcher : TriggerInputMatcher {
    override fun match(input: String, trigger: Trigger): Boolean
        = Regex(trigger.input.value).matches(input)
}
