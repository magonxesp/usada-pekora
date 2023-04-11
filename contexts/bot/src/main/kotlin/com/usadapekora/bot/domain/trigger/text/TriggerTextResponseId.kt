package com.usadapekora.bot.domain.trigger.text

import com.usadapekora.bot.domain.shared.valueobject.UuidValueObject

data class TriggerTextResponseId(override val value: String) : UuidValueObject(value = value)
