package com.ipoondev.android.psugo.model

import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class Item {
    var name: String? = null
    var geoPoint: GeoPoint? = null
    var timeout: Long? = null
    var radius: Float? = null
    @ServerTimestamp
    var timestamp: Date? = null
    var point: Int? = null

    constructor() {}

    constructor(name: String, geoPoint: GeoPoint, timeout: Long, radius: Float, timestamp: Date?, point: Int) {
        this.name = name
        this.geoPoint = geoPoint
        this.timeout = timeout
        this.radius = radius
        this.timestamp = timestamp
        this.point = point
    }


}