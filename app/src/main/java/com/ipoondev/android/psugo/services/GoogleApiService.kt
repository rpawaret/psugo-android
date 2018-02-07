package com.ipoondev.android.psugo.services

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places

object GoogleApiService : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    val TAG = GoogleApiService::class.simpleName

    lateinit var client: GoogleApiClient

    fun buildGoogleApiClient(context: Context) {

        client = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build()

    }

    override fun onConnected(p0: Bundle?) {
        Log.d("GoogleApiService", "GoogleApiClient is Connected")
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d("GoogleApiService", "GoogleApiClient is Suspended")

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("GoogleApiService", "GoogleApiClient Connection Failed")
    }

}