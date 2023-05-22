package com.usadapekora.auth.application.oauth

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.oauth.*
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserRepository
import kotlinx.datetime.Clock

class OAuthUserAccessAuthorizer(
    private val providerFactory: OAuthProviderFactory,
    private val userRepository: UserRepository,
    private val grantCodeRepository: OAuthAuthorizationGrantRepository,
    private val grantCodeCreator: OAuthAuthorizationGrantCodeCreator,
    private val clock: Clock
) {

    suspend fun handle(provider: String, code: String): Either<OAuthProviderError.CallbackError, OAuthAuthorizationGrant.OAuthAuthorizationGrantCode> {
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

        val grantCode = OAuthAuthorizationGrant.fromPrimitives(
            code = grantCodeCreator.fromOAuthUser(providerUser, user.id).value,
            user = user.id.value,
            expiresAt = 30,
            issuedAt = clock.now()
        )

        grantCodeRepository.save(grantCode)

        return grantCode.code.right()
    }

}
