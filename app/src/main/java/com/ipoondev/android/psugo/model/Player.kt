package com.ipoondev.android.psugo.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class Player {
    var playerId: String? = null
    var playerName: String? = null
    var playerEmail: String? = null
    var score: Int = 0
    var currentMissionId: String? = null
    @ServerTimestamp
    var registered_at: Date? = null

    constructor() {}

    constructor(user: FirebaseUser, score: Int, currentMissionId: String?, registered_at: Date) {
        this.playerId = user.uid
        this.playerName = user.displayName
        this.playerEmail = user.email
        if (user.displayName == null) {
            this.playerName = user.email
        }
        this.score = score
        this.currentMissionId = currentMissionId
        this.registered_at = registered_at
    }

}