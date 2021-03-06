package com.sa.facebook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sa.AuthData;
import com.sa.AuthDataHolder;
import com.sa.DialogFactory;
import com.sa.SimpleAuthActivity;
import com.sa.SocialUser;
import com.sa.utils.DeviceUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FacebookAuthActivity extends SimpleAuthActivity
        implements FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback {

    private static final String PROFILE_PIC_URL = "https://graph.facebook.com/%1$s/picture?type=large";
    private static final List<String> DEFAULT_SCOPES = Arrays.asList("email", "public_profile");

    private CallbackManager callbackManager;
    private ProgressDialog loadingDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, FacebookAuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingDialog = DialogFactory.createLoadingDialog(this);

        callbackManager = CallbackManager.Factory.create();

        if (DeviceUtils.isFacebookInstalled(this)) {
            LoginManager.getInstance().logOut();
        }

        LoginManager.getInstance().registerCallback(callbackManager, this);

        LoginManager.getInstance().logInWithReadPermissions(this, getScopes());
    }

    private List<String> getScopes() {
        if (getAuthData() != null && getAuthData().getScopes() != null && getAuthData().getScopes().size() > 0) {
            List<String> scopes = getAuthData().getScopes();
            if (scopes.size() <= 0) {
                scopes = DEFAULT_SCOPES;
            } else if (!scopes.contains(DEFAULT_SCOPES.get(0))) {
                scopes.add(DEFAULT_SCOPES.get(0));
            } else if (!scopes.contains(DEFAULT_SCOPES.get(1))) {
                scopes.add(DEFAULT_SCOPES.get(1));
            }
            return scopes;
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    protected AuthData getAuthData() {
        return AuthDataHolder.getInstance().facebookAuthData;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        loadingDialog.show();
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,link");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {
        handCancel();
    }

    @Override
    public void onError(FacebookException error) {
        handleError(error);
        if (error instanceof FacebookAuthorizationException) {
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        try {
            if (object != null) {
                SocialUser user = new SocialUser();
                if (object.has("id"))
                    user.userId = object.optString("id", "");
                user.accessToken = AccessToken.getCurrentAccessToken().getToken();
                user.profilePictureUrl = String.format(PROFILE_PIC_URL, user.userId);
                if (object.has("email"))
                    user.email = object.optString("email", "");
                if (object.has("name"))
                    user.fullName = object.optString("name", "");
                if (object.has("link"))
                    user.pageLink = object.optString("link", "");
                loadingDialog.dismiss();
                handleSuccess(user);
            } else {
                finish();

            }
        } catch (Exception e) {
            finish();

        }

    }
}
