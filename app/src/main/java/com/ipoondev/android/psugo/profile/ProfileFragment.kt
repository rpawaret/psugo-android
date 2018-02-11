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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.auth.AuthUiActivity
import com.ipoondev.android.psugo.settings.SettingsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.profile_header_main.*

class ProfileFragment : Fragment() {
    private val TAG = ProfileFragment::class.simpleName
    private var user : FirebaseUser? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = FirebaseAuth.getInstance().currentUser

        button_profile_login.setOnClickListener {
            if (user == null) {
                signIn()
            } else {
                signOut()
            }
        }

        button_profile_settings.setOnClickListener {
            val settingsIntent = Intent(activity, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

        populateProfile()
        updateLoginButton()
    }

    private fun populateProfile() {
        if (user?.photoUrl != null) {
            Picasso.with(activity)
                    .load(user?.photoUrl)
                    .placeholder(R.drawable.ic_home_black_24dp)
                    .resize(70, 70)
                    .centerCrop()
                    .into(image_profile)
        }
        text_user_name.text = user?.displayName ?: "No User"
        text_user_email.text = user?.email ?: "No email"
    }

    private fun signIn() {
        val authIntent = Intent(activity, AuthUiActivity::class.java)
        startActivity(authIntent)
    }

    private fun signOut() {
        AuthUI.getInstance()
                .signOut(activity!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Sign out Successful")
                        populateProfile()
                        updateLoginButton()
                    } else {
                        Toast.makeText(activity, "Sign out failed", Toast.LENGTH_LONG).show()
                    }
                }
    }


    private fun updateLoginButton() {
        if (user == null) {
            button_profile_login.text = "Login"
        } else {
            button_profile_login.text = "Logout"
        }
    }


}
