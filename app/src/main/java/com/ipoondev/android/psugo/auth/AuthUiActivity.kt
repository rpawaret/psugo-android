package com.ipoondev.android.psugo.auth

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.ipoondev.android.psugo.MainActivity
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.services.afterSignIn
import kotlinx.android.synthetic.main.activity_auth_ui.*
import java.util.*

class AuthUiActivity : AppCompatActivity() {
    private val TAG = AuthUiActivity::class.simpleName
    private val GOOGLE_TOS_URL = "https://www.google.com/policies/terms/"
    private val RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_ui)
        signIn()
    }

    private fun signIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(getSelectedProviders())
                        .setTosUrl(GOOGLE_TOS_URL)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN
        )
    }

    private fun getSelectedProviders(): MutableList<AuthUI.IdpConfig> {
        val selectedProviders = ArrayList<AuthUI.IdpConfig>()
        selectedProviders.add(AuthUI.IdpConfig.GoogleBuilder().build())
        selectedProviders.add(AuthUI.IdpConfig.EmailBuilder().build())

        return selectedProviders
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data)
            return
        }

        showSnackbar("Unexpected onActivityResult response code")
    }

    private fun handleSignInResponse(resultCode: Int, data: Intent?) {
        val response = IdpResponse.fromResultIntent(data)

        // Successfully signed in
        if (resultCode == RESULT_OK) {
            Log.d(TAG, "SIGN IN SUCCESSFUL")
            afterSignIn()
            startMainActivity()
            finish()
            return
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                showSnackbar(resources.getString(R.string.sign_in_cancelled))
                return
            }

            if (response.errorCode == ErrorCodes.NO_NETWORK) {
                showSnackbar(resources.getString(R.string.no_internet_connection))
                return
            }

            if (response.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                showSnackbar(resources.getString(R.string.unknown_error))
                return
            }
        }

        showSnackbar(resources.getString(R.string.unknown_sign_in_response))
    }

    private fun showSnackbar(errorMessage: String) {
        Snackbar.make(root, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    private fun startMainActivity() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

}
