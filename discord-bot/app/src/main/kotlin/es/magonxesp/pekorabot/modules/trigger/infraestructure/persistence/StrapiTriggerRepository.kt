package es.magonxesp.pekorabot.modules.trigger.infraestructure.persistence

import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.client.StrapiFilter
import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.client.StrapiRequest
import es.magonxesp.pekorabot.modules.trigger.domain.Trigger
import es.magonxesp.pekorabot.modules.trigger.infraestructure.strapi.trigger.TriggerModel
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerRepository
import kotlinx.coroutines.runBlocking
import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.model.Collection


class StrapiTriggerRepository : TriggerRepository {

    private fun mapModelCollectionToAggregate(collection: Collection<TriggerModel>): Array<Trigger> = collection.data.map {
        trigger ->
        trigger.toAggregate()
    }.toTypedArray()

    override fun all(): Array<Trigger> = runBlocking {
        val response = StrapiRequest("triggers").get(populate = arrayOf("output_audio"))

        if (response != null) {
            mapModelCollectionToAggregate(response.toModelCollection())
        } else {
            arrayOf()
        }
    }

    override fun findByDiscordServer(id: String) = runBlocking {
        val response = StrapiRequest("triggers").get(
            filters = arrayOf(StrapiFilter("discord_server_id", id)),
            populate = arrayOf("output_audio")
        )

        if (response != null) {
            mapModelCollectionToAggregate(response.toModelCollection())
        } else {
            arrayOf()
        }
    }

    override fun save(trigger: Trigger) {

    }
}
