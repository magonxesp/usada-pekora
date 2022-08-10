package es.magonxesp.pekorabot.modules.trigger.domain.matcher

import es.magonxesp.pekorabot.modules.trigger.domain.Trigger

class TriggerInMatcher : TriggerInputMatcher {
    override fun match(input: String, trigger: Trigger): Boolean
        = input.lowercase().contains(trigger.input.lowercase())
}
