package com.cashu.socialmedialauth.twitter

import com.cashu.socialmedialauth.AuthCallback
import com.cashu.socialmedialauth.AuthData
import com.cashu.socialmedialauth.AuthDataHolder
import com.cashu.socialmedialauth.CookiesUtils
import com.cashu.socialmedialauth.twitter.TwitterAuthActivity
import com.cashu.socialmedialauth.*
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
