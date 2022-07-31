package es.magonxesp.pekorabot.modules.trigger.infraestructure.persistence

import es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.apiResourceUrl
import es.magonxesp.pekorabot.modules.trigger.domain.Trigger
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerRepository
import es.magonxesp.pekorabot.modules.trigger.infraestructure.strapi.trigger.TriggerList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class StrapiTriggerRepository : TriggerRepository {

    private val jsonSerializer = Json {
        ignoreUnknownKeys = true
    }

    override fun all(): Array<Trigger> {
        val client = HttpClient()

        val json = runBlocking {
            val response = client.get(apiResourceUrl("triggers")) {
                parameter("populate", "output_audio")
                bearerAuth(System.getenv("BACKEND_TOKEN"))
            }

            if (response.status == HttpStatusCode.OK) {
                response.body<String>()
            } else {
                null
            }
        } ?: return arrayOf()

        val list = jsonSerializer.decodeFromString<TriggerList>(json)

        return list.data.map {
            trigger ->
            trigger.toAggregate()
        }.toTypedArray()
    }

}
