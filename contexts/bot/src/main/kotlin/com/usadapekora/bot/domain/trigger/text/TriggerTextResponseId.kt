package com.usadapekora.bot.domain.trigger.text

import com.usadapekora.shared.domain.valueobject.UuidValueObject

data class TriggerTextResponseId(override val value: String) : UuidValueObject(value = value)
