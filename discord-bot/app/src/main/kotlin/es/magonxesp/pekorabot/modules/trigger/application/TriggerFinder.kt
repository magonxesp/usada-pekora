package es.magonxesp.pekorabot.modules.trigger.application

import es.magonxesp.pekorabot.modules.trigger.domain.Trigger
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerException
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerMatcher
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerRepository

class TriggerFinder(
    private val repository: TriggerRepository,
    private val matcher: TriggerMatcher
) {
    suspend fun findByInput(input: String, discordServerId: String): Trigger {
        val triggers = repository.findByDiscordServer(discordServerId)
        return matcher.matchInput(input, triggers) ?: throw TriggerException.NotFound()
    }
}
