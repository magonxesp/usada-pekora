package com.usadapekora.bot.infraestructure.strapi.trigger

import com.usadapekora.bot.infraestructure.strapi.client.StrapiFilter
import com.usadapekora.bot.infraestructure.strapi.client.StrapiRequest
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerRepository
import com.usadapekora.bot.infraestructure.strapi.resources.Collection
import com.usadapekora.bot.domain.trigger.TriggerException
import kotlinx.coroutines.runBlocking


class StrapiTriggerRepository : TriggerRepository {

    private fun mapModelCollectionToAggregate(collection: Collection<TriggerModel>): Array<Trigger>
        = collection.data.map {
            trigger ->
            trigger.toAggregate()
        }.toTypedArray()

    override fun all(): Array<Trigger> {
        val response = runBlocking {
            StrapiRequest("triggers").get(populate = arrayOf("output_audio"))
        }

        if (response != null) {
            return mapModelCollectionToAggregate(response.toModelCollection())
        }

        return arrayOf()
    }

    override fun findByDiscordServer(id: Trigger.TriggerDiscordGuildId): Array<Trigger> {
        val response = runBlocking {
            StrapiRequest("triggers").get(
                filters = arrayOf(StrapiFilter("discord_server_id", id.value)),
                populate = arrayOf("output_audio")
            )
        }

        if (response != null) {
            return mapModelCollectionToAggregate(response.toModelCollection())
        }

        return arrayOf()
    }

    override fun save(trigger: Trigger) { }

    override fun find(id: Trigger.TriggerId): Trigger {
        throw TriggerException.NotFound()
    }

    override fun delete(trigger: Trigger) { }
}
