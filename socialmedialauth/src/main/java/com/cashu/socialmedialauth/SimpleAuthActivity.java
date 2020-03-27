package com.cashu.socialmedialauth;

import android.os.Bundle;

import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class SimpleAuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    protected void handCancel() {
        try {
            if (getAuthData() != null && getAuthData().getCallback() != null)
                getAuthData().getCallback().onCancel();
            finish();
        } catch (Exception e) {
            finish();

        }
    }

    protected void handleError(Throwable error) {
        try {
            if (getAuthData() != null && getAuthData().getCallback() != null && error != null)
                getAuthData().getCallback().onError(error);
            finish();
        } catch (Exception e) {
            finish();

        }

    }

    protected void handleSuccess(SocialUser user) {
        try {
            if (getAuthData() != null && getAuthData().getCallback() != null && user != null)
                getAuthData().getCallback().onSuccess(user);
            finish();
        } catch (Exception e) {
            finish();

        }
    }

    protected abstract AuthData getAuthData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getAuthData() != null)
            getAuthData().clearCallback();
    }
}
