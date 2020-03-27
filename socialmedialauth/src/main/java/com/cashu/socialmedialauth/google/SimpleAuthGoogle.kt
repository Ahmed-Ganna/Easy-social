package com.cashu.socialmedialauth.google


import com.cashu.socialmedialauth.AuthCallback
import com.cashu.socialmedialauth.AuthData
import com.cashu.socialmedialauth.AuthDataHolder
import com.cashu.socialmedialauth.google.GoogleAuthActivity
import com.cashu.socialmedialauth.Initializer

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
