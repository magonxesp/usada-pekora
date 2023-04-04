package com.usadapekora.bot.domain.trigger.matcher

import com.usadapekora.bot.domain.trigger.Trigger

interface TriggerInputMatcher {
    fun match(input: String, trigger: Trigger): Boolean
}
