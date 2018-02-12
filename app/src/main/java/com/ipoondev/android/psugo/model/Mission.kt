package com.ipoondev.android.psugo.model

import android.text.TextUtils
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ServerTimestamp
import java.util.*


class Mission {
    var ownerId: String? = null
    var ownerName: String? = null
    var name: String? = null
    var detail: String? = null
    var subject: String? = null
    var state: String? = null
    var photo: String? = null
    var numPlayer: Int = 0
    @ServerTimestamp
    var timestamp: Date? = null

    constructor() {}

    constructor(user: FirebaseUser, name: String, detail: String, subject: String, state: String, photo: String, numPlayer: Int, timestamp: Date?) {
        this.ownerId = user.uid
        this.ownerName = user.displayName
        if (TextUtils.isEmpty(this.ownerName)) {
            this.ownerName = user.email
        }
        this.name = name
        this.detail = detail
        this.subject = subject
        this.state = state
        this.photo = photo
        this.numPlayer = numPlayer
        this.timestamp = timestamp
    }


}

