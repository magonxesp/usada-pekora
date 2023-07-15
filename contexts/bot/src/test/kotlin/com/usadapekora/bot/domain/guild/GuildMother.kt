package com.usadapekora.bot.domain.guild

import com.usadapekora.bot.domain.Random
import com.usadapekora.shared.domain.ObjectMother
import kotlin.random.Random as KotlinRandom

object GuildMother : ObjectMother<Guild> {

    fun create(
        id: String? = null,
        name: String? = null,
        iconUrl: String? = null,
        providerId: String? = null,
        provider: String? = null,
    ) = Guild.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        name = name ?: Random.instance().overwatch.locations(),
        iconUrl = iconUrl ?: Random.instance().internet.slug().let { "https://example.com/$it.png" },
        providerId = providerId ?: Random.instance().random.nextUUID(),
        provider = provider ?: Random.instance().random.nextEnum(GuildProvider::class.java).value,
    )

    fun createList(
        id: String? = null,
        name: String? = null,
        iconUrl: String? = null,
        providerId: String? = null,
        provider: String? = null,
    ) = (1..KotlinRandom.nextInt(10)).map {
        create(
            id = id,
            name = name,
            iconUrl = iconUrl,
            providerId = providerId,
            provider = provider,
        )
    }

    override fun random(): Guild = create()

}
