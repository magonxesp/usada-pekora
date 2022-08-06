package es.magonxesp.pekorabot.modules.shared.infraestructure.strapi.client

data class StrapiFilter (
    val field: String,
    val value: String,
    val operator: StrapiFilterOperator = StrapiFilterOperator.EQUALS,
) {
    enum class StrapiFilterOperator(val value: String) {
        EQUALS("\$eq")
    }
}
