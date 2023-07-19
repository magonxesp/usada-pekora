package com.usadapekora.bot.domain.trigger

interface BuiltInTriggerRepository {
    fun findAll(): Array<Trigger>
}
