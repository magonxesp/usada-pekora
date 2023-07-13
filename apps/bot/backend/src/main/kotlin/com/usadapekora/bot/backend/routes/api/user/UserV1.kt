package com.usadapekora.bot.backend.routes.api.user

import com.usadapekora.bot.backend.testMode
import com.usadapekora.shared.application.user.find.UserFinder
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.infrastructure.ktor.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val finder: UserFinder by inject(UserFinder::class.java)

private fun errorStatusCode(error: UserException) = when(error) {
    is UserException.NotFound -> HttpStatusCode.NotFound
    else -> HttpStatusCode.InternalServerError
}

fun Route.userV1() {
    authenticate(optional = environment?.testMode ?: false) {
        route("/api/v1/user") {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()

                if (userId != null) {
                    finder.find(User.UserId(userId))
                        .onRight { call.respond(it) }
                        .onLeft { call.respondError(errorStatusCode(it), it.message ?: "") }
                } else {
                    call.respondError(HttpStatusCode.Unauthorized, "The user is not authenticated")
                }
            }
        }
    }
}
