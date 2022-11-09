package com.usadapekora.context.trigger.domain.matcher

import com.usadapekora.context.trigger.domain.Trigger

interface TriggerInputMatcher {
    fun match(input: String, trigger: Trigger): Boolean
}
