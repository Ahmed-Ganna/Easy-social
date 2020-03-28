package com.cashu.spcialsdkexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sa.AuthCallback
import com.sa.SimpleAuth
import com.sa.SocialUser
import java.util.*

class MainActivity : AppCompatActivity(), AuthCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

    }

    fun connectFacebook(v :View) {
        val scopes =
            Arrays.asList("email")
        SimpleAuth.connectFacebook(scopes,this)
    }

    override fun onSuccess(p0: SocialUser?) {
        Log.d("onSuccess",p0.toString())

    }

    override fun onCancel() {
        Log.d("onCancel","")

    }

    override fun onError(p0: Throwable?) {

        Log.d("onError",p0.toString())
    }
}
