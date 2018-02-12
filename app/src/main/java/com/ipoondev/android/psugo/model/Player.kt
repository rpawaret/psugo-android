package com.ipoondev.android.psugo.model

import com.google.firebase.auth.FirebaseUser

class Player {
    var playerId: String? = null
    var playerName: String? = null
    var score: Int = 0

    constructor() {}

    constructor(user: FirebaseUser, score: Int) {
        this.playerId = user.uid
        this.playerName = user.displayName
        if (user.displayName == null) {
            this.playerName = user.email
        }
        this.score = score
    }

}