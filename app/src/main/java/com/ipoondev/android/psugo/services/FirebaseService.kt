package com.ipoondev.android.psugo.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.model.Player
import java.util.*


fun rootDocument(): DocumentReference? = rootPath()?.let {
    return fireStore().document(it)
}

fun rootPath(): String? {
    val loggedInUser = loggedInUser()
    if (loggedInUser != null) {
        return "players/${loggedInUser.uid}"
    }
    return null
}

fun fireStore() = FirebaseFirestore.getInstance()

fun createWriteBatch() = fireStore().batch()

fun loggedInUser() = fireAuth().currentUser

fun fireAuth(): FirebaseAuth = FirebaseAuth.getInstance()

fun afterSignIn() {

    val rootDocument = rootDocument()
            ?: throw IllegalStateException("root document not found")

    rootDocument.get().addOnCompleteListener {
        val isNewUser = it.result.exists().not()

        if (isNewUser) {
            val batch = createWriteBatch()
            val newPlayer = Player(loggedInUser()!!, 0, "", Date())
//            batch.set(rootDocument, HashMap<Any, Any>().apply {
//                put("registered_at", System.currentTimeMillis())
//            })
            batch.set(rootDocument, newPlayer)

            batch.commit().addOnCompleteListener {
                println("thes is a new user")
//                initNewPlayer()
            }

        } else {
            println("this is not a new user")
        }
    }
}



