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
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.auth.AuthUiActivity
import com.ipoondev.android.psugo.services.DataService
import com.ipoondev.android.psugo.settings.SettingsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.profile_header_main.*

class ProfileFragment : Fragment() {
    private val TAG = ProfileFragment::class.simpleName
    private var user: FirebaseUser? = null

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
            startSettingActivity()
        }

        button_profile_add_players.setOnClickListener {
            addPlayers()
        }

        button_profile_add_missions.setOnClickListener {
            addMissions()
        }

        button_profile_add_items.setOnClickListener {
            addItems()
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

    private fun startSettingActivity() {
        val settingsIntent = Intent(activity, SettingsActivity::class.java)
        startActivity(settingsIntent)
    }


    private fun updateLoginButton() {
        if (user == null) {
            button_profile_login.text = "Log in"
        } else {
            button_profile_login.text = "Log out"
        }
    }

    fun addPlayers() {
        val DocRef = FirebaseFirestore.getInstance().collection("players").document()

        for (player in DataService.player) {
            DocRef.set(player)
                    .addOnSuccessListener {
                        Log.d(TAG, "Players has been saved")
                    }.addOnFailureListener {
                        Log.d(TAG, "Players was not saved!: ${it.localizedMessage}")
                    }
        }
    }

    fun addMissions(){
        val docRef = FirebaseFirestore.getInstance().collection("missions")

        for (mission in DataService.missions) {
            docRef.document().set(mission)
                    .addOnSuccessListener {
                        Log.d(TAG, "Missions has been saved")
                    }.addOnFailureListener {
                        Log.d(TAG, "Missions was not saved!: ${it.localizedMessage}")
                    }
        }

    }

    fun addItems() {

        val docRef = FirebaseFirestore.getInstance().collection("items")

        for (item in DataService.items1) {
            docRef.document(item.address!!).set(item)
                    .addOnSuccessListener {
                        Log.d(TAG, "Items has been saved")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Items was not saved!: ${it.localizedMessage}")
                    }

        }

    }






}
