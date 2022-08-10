package es.magonxesp.pekorabot.modules.trigger.domain.matcher

import es.magonxesp.pekorabot.modules.trigger.domain.Trigger

interface TriggerInputMatcher {
    fun match(input: String, trigger: Trigger): Boolean
}
