package com.ipoondev.android.psugo

import android.app.Application
import android.util.Log
import com.ipoondev.android.psugo.services.GoogleApiService

class PSUGO : Application() {

    override fun onCreate() {
        super.onCreate()

        GoogleApiService.buildGoogleApiClient(this)
        GoogleApiService.client.connect()
        Log.d("PSUGO", "Google API Client is connected : ${GoogleApiService.client.isConnected}")
    }


}