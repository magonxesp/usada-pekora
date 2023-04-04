package com.usadapekora.bot.domain.trigger.matcher

import com.usadapekora.bot.domain.trigger.Trigger

class TriggerInMatcher : TriggerInputMatcher {
    override fun match(input: String, trigger: Trigger): Boolean
        = input.lowercase().contains(trigger.input.value.lowercase())
}
