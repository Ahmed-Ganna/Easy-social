package com.sa

import com.sa.facebook.FacebookAuthActivity
import com.sa.google.GoogleAuthActivity
import com.sa.instagram.InstagramAuthActivity
import com.sa.twitter.TwitterAuthActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.twitter.sdk.android.core.TwitterCore

object SimpleAuth {
    @JvmStatic
    fun connectFacebook(scopes: List<String> = listOf(), listener: AuthCallback) {
        AuthDataHolder.getInstance().facebookAuthData = AuthData(scopes, listener)
        FacebookAuthActivity.start(Initializer.context)
    }

    @JvmStatic
    fun disconnectFacebook() {
        AuthDataHolder.getInstance().facebookAuthData = null
        LoginManager.getInstance().logOut()
    }

    @JvmStatic
    fun revokeFacebook(callback: RevokeCallback? = null) {
        GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/permissions/", null, HttpMethod.DELETE
        ) { _ ->
            disconnectFacebook()
            callback?.onRevoked()
        }.executeAsync()
    }


    @JvmStatic
    fun connectGoogle(scopes: List<String> = listOf(), listener: AuthCallback) {
        AuthDataHolder.getInstance().googleAuthData = AuthData(scopes, listener)
        GoogleAuthActivity.start(Initializer.context)
    }

    @JvmStatic
    fun disconnectGoogle() {
        AuthDataHolder.getInstance().googleAuthData = null
        GoogleAuthActivity.setGoogleDisconnectRequested(Initializer.context,true)
    }

    @JvmStatic
    fun revokeGoogle() {
        GoogleAuthActivity.setGoogleRevokeRequested(Initializer.context,true)
    }


    @JvmStatic
    fun connectTwitter(listener: AuthCallback) {
        AuthDataHolder.getInstance().twitterAuthData = AuthData(listOf(), listener)
        TwitterAuthActivity.start(Initializer.context)
    }

    @JvmStatic
    fun disconnectTwitter() {
        AuthDataHolder.getInstance().twitterAuthData = null
        TwitterCore.getInstance().sessionManager.clearActiveSession()
        CookiesUtils.clearCookies(Initializer.context)
    }


    @JvmStatic
    fun connectInstagram(scopes: List<String> = listOf(), listener: AuthCallback) {
        AuthDataHolder.getInstance().instagramAuthData = AuthData(scopes, listener)
        InstagramAuthActivity.start(Initializer.context)
    }

    @JvmStatic
    fun disconnectInstagram() {
        AuthDataHolder.getInstance().instagramAuthData = null
        CookiesUtils.clearCookies(Initializer.context)
    }
}

