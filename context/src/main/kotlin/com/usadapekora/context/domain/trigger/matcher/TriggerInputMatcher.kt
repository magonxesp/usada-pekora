package com.usadapekora.context.domain.trigger.matcher

import com.usadapekora.context.domain.trigger.Trigger

interface TriggerInputMatcher {
    fun match(input: String, trigger: Trigger): Boolean
}
