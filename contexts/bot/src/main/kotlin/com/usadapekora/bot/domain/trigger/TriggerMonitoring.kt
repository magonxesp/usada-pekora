package com.usadapekora.bot.domain.trigger

interface TriggerMonitoring {
    fun triggerInputNotMatched()
    fun triggerInputMatched()
    fun triggerTextResponseSent()
    fun triggerAudioResponseSent()
}
