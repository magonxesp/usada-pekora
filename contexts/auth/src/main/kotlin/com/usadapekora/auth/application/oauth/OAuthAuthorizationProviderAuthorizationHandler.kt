package com.usadapekora.auth.application.oauth

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.oauth.*
import com.usadapekora.auth.domain.shared.AuthorizationGrant
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository
import com.usadapekora.shared.domain.IdCreator
import com.usadapekora.shared.domain.auth.AuthorizationGrantedEvent
import com.usadapekora.shared.domain.bus.EventBus
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserRepository
import kotlinx.datetime.Clock
import java.util.UUID

class OAuthAuthorizationProviderAuthorizationHandler(
    private val idCreator: IdCreator,
    private val providerFactory: OAuthProviderFactory,
    private val userRepository: UserRepository,
    private val grantCodeRepository: AuthorizationGrantRepository,
    private val grantCodeCreator: OAuthAuthorizationGrantCodeCreator,
    private val clock: Clock,
    private val eventBus: EventBus
) {

    suspend fun handle(provider: String, code: String): Either<OAuthProviderError.CallbackError, AuthorizationGrant.AuthorizationGrantCode> {
        val providerEnum = Either.catch { OAuthProvider.fromValue(provider) }.let {
            if (it.isLeft()) return OAuthProviderError.CallbackError("The $provider provider is not available").left()
            it.getOrNull()!!
        }

        val providerInstance = providerFactory.getInstance(providerEnum).let {
            if (it.isLeft()) return OAuthProviderError.CallbackError("The $provider provider is not available").left()
            it.getOrNull()!!
        }

        val providerUser = providerInstance.handleCallback(code).let {
            if (it.isLeft()) return it.leftOrNull()!!.left()
            it.getOrNull()!!
        }

        var user = userRepository.findByDiscordId(User.DiscordUserId(providerUser.id)).getOrNull()

        if (user == null) {
            user = User.fromPrimitives(
                id = providerUser.nextDomainUserId,
                name = providerUser.name ?: "unnamed",
                avatar = providerUser.avatar,
                discordId = providerUser.id
            )

            userRepository.save(user)
        }

        val grantCode = AuthorizationGrant.fromPrimitives(
            code = grantCodeCreator.fromOAuthUser(providerUser, user.id).value,
            user = user.id.value,
            expiresAt = 30,
            issuedAt = clock.now()
        )

        grantCodeRepository.save(grantCode)

        eventBus.dispatch(AuthorizationGrantedEvent(
            id = idCreator.create(),
            occurredOn = clock.now().toString(),
            userId = user.id.value
        )).onLeft { return OAuthProviderError.CallbackError(it.message).left() }

        return grantCode.code.right()
    }

}
