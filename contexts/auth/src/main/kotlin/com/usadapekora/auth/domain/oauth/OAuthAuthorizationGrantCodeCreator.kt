package com.usadapekora.auth.domain.oauth

import com.usadapekora.auth.domain.shared.AuthorizationGrant
import com.usadapekora.shared.domain.auth.OAuthUser
import com.usadapekora.shared.domain.user.User

interface OAuthAuthorizationGrantCodeCreator {
    fun fromOAuthUser(oAuthUser: OAuthUser, userId: User.UserId): AuthorizationGrant.AuthorizationGrantCode
}
