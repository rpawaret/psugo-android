package com.ipoondev.android.psugo.model

import com.google.firebase.firestore.GeoPoint

class Item {
    var id: Int = 0
    var title: String? = null
    var address: String? = null
    var geoPoint: GeoPoint? = null
    var timeout: Long? = null
    var radius: Float? = null

    constructor() {}

    constructor(id: Int, title: String, address: String, geoPoint: GeoPoint, timeOut: Long?, radius: Float?) {
        this.id = id
        this.title = title
        this.address = address
        this.geoPoint = geoPoint
        this.timeout = timeOut
        this.radius = radius
    }
}