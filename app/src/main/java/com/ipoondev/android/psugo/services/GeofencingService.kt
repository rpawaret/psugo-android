package com.ipoondev.android.psugo.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.ipoondev.android.psugo.geofencing.GeofenceTransitionsIntentService
import com.ipoondev.android.psugo.model.Item

object GeofencingService {

    private val pendingIntent: PendingIntent? = null
    lateinit var geofenceList: MutableList<Geofence>
    var isRegisterComplete = false
    var isUnregisterComplete = false

    fun createGeofenceList(items: List<Item>) {

        geofenceList = arrayListOf()

        for (item in items) {
            val geofence = Geofence.Builder()
                    .setRequestId(item.name)
                    .setExpirationDuration((item.timeout!! * 60 * 60 * 1000))
                    .setCircularRegion(item.geoPoint!!.latitude, item.geoPoint!!.longitude, item.radius!!)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build()
                geofenceList.add(geofence)
        }

        Log.d("GeofencingService", geofenceList.toString())
    }

    fun registerAllGeofences(context: Context) {
        if (!GoogleApiService.client.isConnected) {
            Log.d("GeofencingService", "Google API Client is not connected")
            return
        }

        try {
            LocationServices.getGeofencingClient(context).addGeofences(
                    getGeofenceingRequest(geofenceList),
                    getGeofencePendingIntent(context)
            )
                    .addOnCompleteListener { task ->
                        Log.d("GeofencingService", "registerAllGeofences is complete: ${task.isComplete}")
                        isRegisterComplete = task.isComplete
                    }
                    .addOnFailureListener { exception ->
                        Log.d("GeofencingService", "registerAllGeofences is : ${exception.localizedMessage}")

                    }
        } catch (e: SecurityException) {
            Log.d("GeofencingService", e.localizedMessage)
        }
    }

    fun unRegisterAllGeofences(context: Context) {
        if (!GoogleApiService.client.isConnected) {
            Log.d("GeofencingService", "Google API Client is not connected")
            return
        }

        try {
            LocationServices.getGeofencingClient(context).removeGeofences(
                    getGeofencePendingIntent(context)
            )
                    .addOnCompleteListener { task ->
                        Log.e("GeofenceService", "unRegisterAllGeofences is complete: ${task.isComplete}")
                        isUnregisterComplete = task.isComplete
                    }
                    .addOnFailureListener { exception ->
                        Log.e("GeofenceService", "unRegisterAllGeofences is :  ${exception.localizedMessage}")
                    }
        } catch (se: SecurityException) {
            Log.e("GeofencingService", se.localizedMessage)
        }

    }

    private fun getGeofenceingRequest(geofenceList: List<Geofence>): GeofencingRequest {
        val builder = GeofencingRequest.Builder()
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        builder.addGeofences(geofenceList)
        return builder.build()
    }

    private fun getGeofencePendingIntent(context: Context): PendingIntent {

        if (pendingIntent != null) {
            return pendingIntent
        }
        val intent = Intent(context, GeofenceTransitionsIntentService::class.java)
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}