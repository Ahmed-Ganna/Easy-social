package com.cashu.socialmedialauth.facebook

import com.cashu.socialmedialauth.AuthCallback
import com.cashu.socialmedialauth.AuthData
import com.cashu.socialmedialauth.AuthDataHolder
import com.cashu.socialmedialauth.RevokeCallback
import com.cashu.socialmedialauth.facebook.FacebookAuthActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.cashu.socialmedialauth.*

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
    GraphRequest(AccessToken.getCurrentAccessToken(),
      "/me/permissions/", null, HttpMethod.DELETE
    ) { _ ->
      disconnectFacebook()
      callback?.onRevoked()
    }.executeAsync()
  }
}

