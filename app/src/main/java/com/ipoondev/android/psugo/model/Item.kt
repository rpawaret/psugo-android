package com.ipoondev.android.psugo.model

class Item {
    var name: String? = null
    var latitude: String? = null
    var longitude: String? = null
    var timeout: String? = null
    var radius: String? = null
    var quizzes: List<String>? = null

    constructor() {}

    constructor(name: String?, latitude: String?, longitude: String?, timeout: String?, radius: String?, quizzes: ArrayList<String>?) {
        this.name = name
        this.latitude = latitude
        this.longitude = longitude
        this.timeout = timeout
        this.radius = radius
        this.quizzes = quizzes
    }


}