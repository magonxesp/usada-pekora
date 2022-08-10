package es.magonxesp.pekorabot

import es.magonxesp.pekorabot.modules.trigger.application.TriggerFinder
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerMatcher
import es.magonxesp.pekorabot.modules.trigger.infraestructure.persistence.StrapiTriggerRepository

val triggerRepository = StrapiTriggerRepository()

fun triggerFinder() = TriggerFinder(triggerRepository, TriggerMatcher())
