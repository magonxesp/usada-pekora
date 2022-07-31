package es.magonxesp.pekorabot.modules.shared.infraestructure.strapi

fun apiResourceUrl(url: String): String {
    return "${System.getenv("BACKEND_BASE_URL")}/api/${url.removePrefix("/")}"
}
