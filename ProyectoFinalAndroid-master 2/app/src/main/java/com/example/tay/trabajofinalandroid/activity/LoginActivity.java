package com.example.tay.trabajofinalandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.tay.trabajofinalandroid.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    //RequestCode para inicio de sesión con Google.
    private static final int RC_SIGN_IN = 123;
    //Variable Events
    private LoginActivityEvents loginActivityEvents;
    //Btn logout
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.PhoneBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.FacebookBuilder().build(),
                                new AuthUI.IdpConfig.TwitterBuilder().build()))
                        .setTosUrl("https://superapp.example.com/terms-of-service.html")
                        .setPrivacyPolicyUrl("https://superapp.example.com/privacy-policy.html")
                        .build(),
                RC_SIGN_IN);
        loginActivityEvents = new LoginActivityEvents(this);
        btnLogout = findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(loginActivityEvents);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                // startActivity(SignedInActivity.createIntent(this, response));
                //finish();
                return;
            } else {
                // Sign in failed


                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    // showSnackbar(R.string.no_internet_connection);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    //showSnackbar(R.string.unknown_error);
                    return;
                }
            }

            //showSnackbar(R.string.unknown_sign_in_response);
        }
    }

    public LoginActivityEvents getLoginActivityEvents() {
        return loginActivityEvents;
    }

    public void setLoginActivityEvents(LoginActivityEvents loginActivityEvents) {
        this.loginActivityEvents = loginActivityEvents;
    }

}
