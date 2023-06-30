package com.usadapekora.bot.domain.guild

import com.usadapekora.bot.domain.Random
import com.usadapekora.shared.domain.ObjectMother


object GuildMemberMother : ObjectMother<GuildMember> {

    fun create(userId: String? = null, guildId: String? = null) = GuildMember.fromPrimitives(
        user = userId ?: Random.instance().random.nextUUID(),
        guild = guildId ?: Random.instance().random.nextUUID(),
    )

    override fun random() = create()

}
