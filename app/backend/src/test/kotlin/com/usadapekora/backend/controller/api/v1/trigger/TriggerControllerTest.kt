package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.SpringBootHttpTestCase
import com.usadapekora.context.application.trigger.create.TriggerCreateRequest
import com.usadapekora.context.application.trigger.create.TriggerCreator
import org.koin.java.KoinJavaComponent


abstract class TriggerControllerTest : SpringBootHttpTestCase()  {

    private val creator: TriggerCreator by KoinJavaComponent.inject(TriggerCreator::class.java)

    fun createDummy(
        id: String = "29da2a75-f5ba-4bff-99ee-3eb654716284",
        input: String = "peko",
        compare: String = "in",
        outputText: String? = "It's a me pekora",
        discordGuildId: String = "94101459"
    ) {
        creator.create(
            TriggerCreateRequest(
                id = id,
                input = input,
                compare = compare,
                outputText = outputText,
                discordGuildId = discordGuildId
            )
        )
    }

}
