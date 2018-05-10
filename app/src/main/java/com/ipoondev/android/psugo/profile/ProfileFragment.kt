package com.ipoondev.android.psugo.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
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
import com.ipoondev.android.psugo.quiz.QuizActivity
import com.ipoondev.android.psugo.settings.SettingsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.profile_header_main.*

class ProfileFragment : Fragment() {
    private val TAG = ProfileFragment::class.simpleName
    private var currentUser: FirebaseUser? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentUser = FirebaseAuth.getInstance().currentUser

        button_profile_login.setOnClickListener {
            if (currentUser == null) {
                signIn()
            } else {
                showSignOutDialog { signout ->
                    if (signout) {
                        signOut()
                    }
                }
            }
        }

        button_profile_settings.setOnClickListener {
            startSettingActivity()
        }

        populateProfile()
        updateLoginButton()
    }

    private fun startQuizActivity() {
        val quizIntent = Intent(activity, QuizActivity::class.java)
        startActivity(quizIntent)

    }

    private fun populateProfile() {
        if (currentUser?.photoUrl != null) {
            Picasso.with(activity)
                    .load(currentUser?.photoUrl)
                    .placeholder(R.drawable.ic_account_circle_white_24px)
                    .resize(70, 70)
                    .centerCrop()
                    .into(image_profile)
        }
        text_user_name.text = currentUser?.displayName ?: "No User"
        text_user_email.text = currentUser?.email ?: "No email"
        text_user_uid.text = currentUser?.uid ?: "No uid"
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

    private fun showSignOutDialog(complete: (Boolean) -> Unit) {
        val builder = AlertDialog.Builder(activity!!)
                .setMessage("Confirm to Sign out?")
                .setPositiveButton("Sign Out", { dialog, which ->
                    complete(true)
                })
                .setNegativeButton("Cancel", { dialog, which -> dialog.cancel() })

        val dialog = builder.create()
        dialog.show()
    }

    private fun startSettingActivity() {
        val settingsIntent = Intent(activity , SettingsActivity::class.java)
        startActivity(settingsIntent)
    }

    private fun updateLoginButton() {
        if (currentUser == null) {
            button_profile_login.text = "Log in"
        } else {
            button_profile_login.text = "Log out"
        }
    }

}
