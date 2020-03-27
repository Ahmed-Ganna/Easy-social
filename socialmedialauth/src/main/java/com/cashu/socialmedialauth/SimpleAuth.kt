package com.cashu.socialmedialauth

import com.cashu.socialmedialauth.facebook.FacebookAuthActivity
import com.cashu.socialmedialauth.google.GoogleAuthActivity
import com.cashu.socialmedialauth.instagram.InstagramAuthActivity
import com.cashu.socialmedialauth.twitter.TwitterAuthActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.cashu.socialmedialauth.Initializer
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

