package es.magonxesp.pekorabot.modules.trigger.domain.matcher

import es.magonxesp.pekorabot.modules.trigger.domain.Trigger

class TriggerPatternMatcher : TriggerInputMatcher {
    override fun match(input: String, trigger: Trigger): Boolean
        = Regex(trigger.input).matches(input)
}
