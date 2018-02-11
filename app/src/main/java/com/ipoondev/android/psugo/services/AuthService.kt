package com.ipoondev.android.psugo.services

import com.google.firebase.auth.FirebaseAuth

object AuthService {
    private val TAG = AuthService::class.simpleName

    private val auth = FirebaseAuth.getInstance()!!
    var isUserLoggedIn = false
    var currentUser = auth.currentUser

    fun checkIsUserLoggedIn() : Boolean {
        return currentUser != null
    }


}


