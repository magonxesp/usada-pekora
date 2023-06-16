package com.usadapekora.bot.domain.guild

import com.usadapekora.bot.domain.ObjectMother
import com.usadapekora.bot.domain.Random


object GuildMemberMother : ObjectMother<GuildMember> {

    fun create(userId: String? = null, guildId: String? = null) = GuildMember.fromPrimitives(
        user = userId ?: Random.instance().random.nextUUID(),
        guild = guildId ?: Random.instance().random.nextUUID(),
    )

    override fun random() = create()

}
