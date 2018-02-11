package com.ipoondev.android.psugo.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

object AuthService {
    private val TAG = AuthService::class.simpleName

    private val auth = FirebaseAuth.getInstance()!!
    var isUserLoggedIn = false
    var currentUser = auth.currentUser

    fun signUp(email: String, password: String, complete: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { it ->
                    Log.d(TAG, "register Successful: ${it.result}")
                    complete(true)
                }
                .addOnFailureListener {
                    Log.d(TAG, "register fail!: ${it.localizedMessage}")
                    complete(false)
                }
    }

    fun signIn(email: String, password: String, complete: (Boolean) -> Unit) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { it ->
                    if (it.isSuccessful) {
                        Log.d(TAG, "Login Successful")
                        isUserLoggedIn = true
                        currentUser = auth.currentUser
                        Log.d(TAG, "currentUser: ${currentUser?.displayName} ${currentUser?.email}")
                        complete(true)
                    } else {
                        complete(false)

                    }
                }
                .addOnFailureListener { it ->
                    Log.d(TAG, "Login Fail: " + it.localizedMessage)
                    complete(false)
                }

    }

    fun signOut(complete: (Boolean) -> Unit) {
        auth.signOut()
        isUserLoggedIn = false
        currentUser = null
        Log.d(TAG, "currentUser: $currentUser")
        complete(true)
    }

    fun checkUserLoggedIn() : Boolean {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser != null
    }


}


