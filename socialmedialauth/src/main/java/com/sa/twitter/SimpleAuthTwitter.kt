package com.sa.twitter

import com.sa.AuthCallback
import com.sa.AuthData
import com.sa.AuthDataHolder
import com.sa.CookiesUtils
import com.sa.*
import com.twitter.sdk.android.core.TwitterCore

object SimpleAuth {
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
}
