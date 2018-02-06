package com.ipoondev.android.psugo.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

object AuthService {

    val firebaseAuth = FirebaseAuth.getInstance()!!

    fun regiterUser() {

    }

    fun loginUser(email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { it ->
                    if (it.isSuccessful) {
                        Log.d("LoginUser", "Login Successful")
                    } else {

                    }
                }
                .addOnFailureListener { it ->
                    Log.d("LoginUser", "Login Fail: " + it.localizedMessage)
                }

    }


}


