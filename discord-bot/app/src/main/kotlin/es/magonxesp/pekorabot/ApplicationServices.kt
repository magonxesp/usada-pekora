package es.magonxesp.pekorabot

import es.magonxesp.pekorabot.modules.trigger.application.TriggerFinder
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerMatcher
import es.magonxesp.pekorabot.modules.trigger.infraestructure.persistence.StrapiTriggerRepository
import es.magonxesp.pekorabot.modules.video.application.VideoFeedSubscriber
import es.magonxesp.pekorabot.modules.video.infraestructure.youtube.YoutubeFeedSubscriber

val triggerRepository = StrapiTriggerRepository()

fun triggerFinder() = TriggerFinder(triggerRepository, TriggerMatcher())
fun videoFeedSubscriber() = VideoFeedSubscriber(YoutubeFeedSubscriber())
