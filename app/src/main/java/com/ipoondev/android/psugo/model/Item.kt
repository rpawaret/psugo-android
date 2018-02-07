package com.ipoondev.android.psugo.model

import com.google.firebase.firestore.GeoPoint

class Item(val id: Int, val title: String, val address: String, val geoPoint: GeoPoint, val timeout: Long, val radius: Float)
