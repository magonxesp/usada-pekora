package com.usadapekora.auth.domain.oauth

import com.usadapekora.shared.domain.user.User

interface OAuthAuthorizationGrantCodeCreator {
    fun fromOAuthUser(oAuthUser: OAuthUser, userId: User.UserId): OAuthAuthorizationGrant.OAuthAuthorizationGrantCode
}
