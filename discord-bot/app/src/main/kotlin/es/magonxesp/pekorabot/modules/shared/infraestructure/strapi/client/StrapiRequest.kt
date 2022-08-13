package es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.client

import es.magonxesp.pekorabot.backendBaseUrl
import es.magonxesp.pekorabot.backendToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.logging.Logger


class StrapiRequest(
    private val resourceUrl: String,
) {
    private val client = HttpClient()

    fun HttpRequestBuilder.configure() {
        header("Accept", "application/json")
        header("Content-Type", "application/json")

        bearerAuth(backendToken)
    }

    private fun apiResourceUrl(url: String): String {
        return "$backendBaseUrl/api/${url.removePrefix("/")}"
    }

    private suspend fun strapiResponse(response: HttpResponse): StrapiResponse? {
        Logger.getLogger(StrapiRequest::class.toString())
            .info("Strapi request to ${response.request.url} with status ${response.status} and response ${response.body<String>()}")

        if (response.status == HttpStatusCode.OK) {
            return StrapiResponse(response.body())
        }

        return null
    }

    suspend fun get(filters: Array<StrapiFilter>? = null, fields: Array<String>? = null, populate: Array<String>? = null): StrapiResponse?  {
        val response = client.get(apiResourceUrl(resourceUrl)) {
            configure()

            url {
                filters?.forEachIndexed { index, filter -> encodedParameters.append("filters[${filter.field}][${filter.operator.value}][$index]", filter.value) }
                fields?.forEachIndexed { index, field -> encodedParameters.append("fields[$index]", field) }
                populate?.forEachIndexed { index, field -> encodedParameters.append("populate[$index]", field) }
            }
        }

        return strapiResponse(response)
    }

    suspend fun post(body: String): StrapiResponse? {
        val response = client.post(apiResourceUrl(resourceUrl)) {
            configure()
            setBody(body)
        }

        return strapiResponse(response)
    }

}
