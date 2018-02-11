package com.ipoondev.android.psugo.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.auth.AuthActivity
import com.ipoondev.android.psugo.services.AuthService
import com.ipoondev.android.psugo.settings.SettingsActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.profile_header_main.*

class ProfileFragment : Fragment() {
    private val TAG = ProfileFragment::class.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateLoginButton()

        button_profile_login.setOnClickListener {
            if (!AuthService.checkUserLoggedIn()) {
                signIn()
            } else {
                signOut()
            }
        }

        button_profile_settings.setOnClickListener {
            val settingsIntent = Intent(activity, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        updateUI(AuthService.currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        text_user_name.text = currentUser?.displayName ?: "Not have User"
        text_user_email.text = currentUser?.email ?: "Not have Email"

    }

    private fun signIn() {
        val authIntent = Intent(activity, AuthActivity::class.java)
        startActivity(authIntent)
    }

    private fun signOut() {
        AuthUI.getInstance()
                .signOut(activity!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Signout Successful")
                        updateUI(AuthService.currentUser)
                    } else {
                        Toast.makeText(activity, "Sign out failed", Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun updateLoginButton() {
        if (!AuthService.checkUserLoggedIn()) {
            button_profile_login.text = "Login"
        } else {
            button_profile_login.text = "Logout"
        }
    }


}
