package com.ipoondev.android.psugo.model


class Mission {
    var name: String? = null
    var statement: String? = null
    var categories: String? = null
    var selectedItems: List<String>? = null

    constructor() {}

    constructor(name: String?, statement: String?, categories: String?, selectedItems: ArrayList<String>?) {
        this.name = name
        this.statement = statement
        this.categories = categories
        this.selectedItems = selectedItems
    }


//    constructor(user: FirebaseUser, name: String, detail: String, subject: String, state: String, photo: String, numPlayer: Int, timestamp: Date?) {
//        this.ownerId = user.uid
//        this.ownerName = user.displayName
//        if (TextUtils.isEmpty(this.ownerName)) {
//            this.ownerName = user.email
//        }
//        this.name = name
//        this.statement = detail
//        this.categories = subject
//        this.state = state
//        this.photo = photo
//        this.numPlayer = numPlayer
//        this.timestamp = timestamp
//    }


}

