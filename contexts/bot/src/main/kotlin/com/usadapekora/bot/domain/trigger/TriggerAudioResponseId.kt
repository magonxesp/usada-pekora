package com.usadapekora.bot.domain.trigger

import com.usadapekora.bot.domain.shared.valueobject.UuidValueObject

data class TriggerAudioResponseId(override val value: String) : UuidValueObject(value = value)
