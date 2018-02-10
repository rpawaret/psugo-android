package com.ipoondev.android.psugo.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseUser
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.login.LoginActivity
import com.ipoondev.android.psugo.settins.SettingsActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.profile_header_main.*

class ProfileFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_login.setOnClickListener {

            val loginIntent = Intent(activity, LoginActivity::class.java)
            startActivity(loginIntent)


//            if (!AuthService.isUserLoggedIn) {
//                AuthService.signIn("pawares.r@gmail.com", "123456") { complete ->
//                    button_login.text = "Logout"
//                    updateUI(AuthService.currentUser)
//                }
//            } else {
//                AuthService.signOut { complete ->
//                    button_login.text = "Login"
//                    Toast.makeText(context, "LoginOutSuccessful", Toast.LENGTH_LONG).show()
//                    updateUI(AuthService.currentUser)
//
//                }
//
//            }

        }

        button_settings.setOnClickListener {
            val settingsIntent = Intent(activity, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
    }

    override fun onStart() {
        super.onStart()
//        updateUI(AuthService.currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        text_user_name.text = currentUser?.displayName ?: "Not have User"
        text_user_email.text = currentUser?.email ?: "Not have Email"

    }

}
