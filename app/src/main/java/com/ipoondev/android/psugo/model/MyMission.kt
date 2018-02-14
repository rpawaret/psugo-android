package com.ipoondev.android.psugo.model

class MyMission {
    var missionId: String? = null
    var score: Int = 0
    var state: String? = null

    constructor() {}

    constructor(missionId: String?, score: Int, state: String?) {
        this.missionId = missionId
        this.score = score
        this.state = state
    }
}