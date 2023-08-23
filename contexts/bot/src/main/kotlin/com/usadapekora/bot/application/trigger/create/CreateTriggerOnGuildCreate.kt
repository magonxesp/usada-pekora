package com.usadapekora.bot.application.trigger.create

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreator
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseUrlCreateRequest
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreator
import com.usadapekora.bot.domain.trigger.BuiltInTriggerRepository
import com.usadapekora.bot.domain.trigger.audio.BuiltInTriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.text.BuiltInTriggerTextResponseRepository
import com.usadapekora.shared.domain.bus.event.EventSubscriber
import com.usadapekora.shared.domain.bus.event.EventSubscriberError
import com.usadapekora.shared.domain.bus.event.SubscribesEvent
import com.usadapekora.shared.domain.guild.GuildCreatedEvent
import java.util.UUID

@SubscribesEvent<GuildCreatedEvent>(eventClass = GuildCreatedEvent::class)
class CreateTriggerOnGuildCreate(
    private val triggerCreator: TriggerCreator,
    private val triggerAudioResponseCreator: TriggerAudioResponseCreator,
    private val triggerTextResponseCreator: TriggerTextResponseCreator,
    private val builtInTriggerRepository: BuiltInTriggerRepository,
    private val builtInTriggerTextResponseRepository: BuiltInTriggerTextResponseRepository,
    private val builtInTriggerAudioResponseRepository: BuiltInTriggerAudioResponseRepository
) : EventSubscriber<GuildCreatedEvent> {
    override fun handle(event: GuildCreatedEvent): Either<EventSubscriberError, Unit> {
        builtInTriggerRepository.findAll().forEach { trigger ->
            val responseText = builtInTriggerTextResponseRepository.find(trigger.responseText!!)
                .onLeft { return EventSubscriberError(it.message).left() }
                .getOrNull()!!
            val responseAudio = builtInTriggerAudioResponseRepository.find(trigger.responseAudio!!)
                .onLeft { return EventSubscriberError(it.message).left() }
                .getOrNull()!!

            val nextResponseTextId = UUID.randomUUID().toString()
            val nextResponseAudioId = UUID.randomUUID().toString()

            triggerTextResponseCreator.create(TriggerTextResponseCreateRequest(
                id = nextResponseTextId,
                type = responseText.type.value,
                content = responseText.content.value
            )).onLeft { return EventSubscriberError(it.message).left() }

            triggerAudioResponseCreator.create(TriggerAudioResponseUrlCreateRequest(
                id = nextResponseAudioId,
                type = responseAudio.kind.name,
                source = responseAudio.source.value
            )).onLeft { return EventSubscriberError(it.message).left() }

            triggerCreator.create(TriggerCreateRequest(
                id = UUID.randomUUID().toString(),
                title = trigger.title.value,
                input = trigger.input.value,
                compare = trigger.compare.value,
                guildId = event.guildId,
                responseAudioId = nextResponseAudioId,
                responseTextId = nextResponseTextId
            )).onLeft { return EventSubscriberError(it.message).left() }
        }

        return Unit.right()
    }
}
