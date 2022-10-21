package es.magonxesp.pekorabot.modules.trigger.infraestructure.persistence

import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.client.StrapiFilter
import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.client.StrapiRequest
import es.magonxesp.pekorabot.modules.trigger.domain.Trigger
import es.magonxesp.pekorabot.modules.trigger.infraestructure.strapi.trigger.TriggerModel
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerRepository
import kotlinx.coroutines.runBlocking
import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.resources.Collection


class StrapiTriggerRepository : TriggerRepository {

    private fun mapModelCollectionToAggregate(collection: Collection<TriggerModel>): Array<Trigger>
        = collection.data.map {
            trigger ->
            trigger.toAggregate()
        }.toTypedArray()

    override suspend  fun all(): Array<Trigger> {
        val response = StrapiRequest("triggers").get(populate = arrayOf("output_audio"))

        if (response != null) {
            return mapModelCollectionToAggregate(response.toModelCollection())
        }

        return arrayOf()
    }

    override suspend fun findByDiscordServer(id: String): Array<Trigger> {
        val response = StrapiRequest("triggers").get(
            filters = arrayOf(StrapiFilter("discord_server_id", id)),
            populate = arrayOf("output_audio")
        )

        if (response != null) {
            return mapModelCollectionToAggregate(response.toModelCollection())
        }

        return arrayOf()
    }

    override suspend fun save(trigger: Trigger) {

    }
}
