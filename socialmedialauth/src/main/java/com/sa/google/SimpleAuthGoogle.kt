package com.sa.google


import com.sa.AuthCallback
import com.sa.AuthData
import com.sa.AuthDataHolder
import com.sa.Initializer

object SimpleAuth {
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
}
