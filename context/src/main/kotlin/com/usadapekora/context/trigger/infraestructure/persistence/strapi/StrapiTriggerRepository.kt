package com.usadapekora.context.trigger.infraestructure.persistence.strapi

import com.usadapekora.context.shared.infraestructure.strapi.client.StrapiFilter
import com.usadapekora.context.shared.infraestructure.strapi.client.StrapiRequest
import com.usadapekora.context.trigger.domain.Trigger
import com.usadapekora.context.trigger.domain.TriggerRepository
import com.usadapekora.context.shared.infraestructure.strapi.resources.Collection
import com.usadapekora.context.trigger.domain.TriggerException
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
