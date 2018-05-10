package com.ipoondev.android.psugo.geofencing

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.ipoondev.android.psugo.model.Item


class Geofencing(private val mContext: Context, private val item: Item) : OnCompleteListener<Void> {
    private val mGeofencingClient: GeofencingClient = LocationServices.getGeofencingClient(mContext)
    private var mGeofencePendingIntent: PendingIntent? = null
    private var mPendingGeofenceTask = PendingGeofenceTask.NONE
    private var mGeofence: Geofence? = null
    enum class PendingGeofenceTask {
        ADD, REMOVE, NONE
    }

    init {
        createGeofence(item)
    }

    private fun getGeofencingRequest(): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofence(mGeofence)
        }.build()
    }

    private val geofencePendingIntent: PendingIntent?
        get() {
            if (mGeofencePendingIntent != null) {
                return mGeofencePendingIntent
            }
            val intent = Intent(mContext, GeofenceTransitionsIntentService::class.java)
            mGeofencePendingIntent = PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return mGeofencePendingIntent
        }

    fun createGeofence(item: Item) {
        Log.d(TAG, "createGeofence(): hit")

        val timeout = item.timeout!!.toLong()
        val latitude = item.latitude!!.toDouble()
        val longitude = item.longitude!!.toDouble()
        val radius = item.radius!!.toFloat()

        mGeofence = Geofence.Builder()
                .setRequestId(item.name)
                .setExpirationDuration(timeout.times(60).times(60).times(1000))
                .setCircularRegion(latitude, longitude, radius)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
    }

    fun performPendingGeofenceTask(task: PendingGeofenceTask) {
        Log.d(TAG, "performPendingGeofenceTask(): hit")
        when (task) {
            PendingGeofenceTask.ADD -> addGeofences()
            PendingGeofenceTask.REMOVE -> removeGeofences()
            else -> Log.e("Geofencing", "Could not Perform")
        }
    }

    private fun addGeofences() {
        Log.d(TAG, "addGeofences(): hit")
        if (checkPermissions()) {
            try {
                mGeofencingClient.addGeofences(getGeofencingRequest(), geofencePendingIntent)
                        .addOnCompleteListener(this)
            } catch (se: SecurityException) {
                Log.e(TAG, se.localizedMessage)
            }
        } else {
            Toast.makeText(mContext, "Insufficient permissions.", Toast.LENGTH_LONG).show()
            return
        }

    }

    private fun removeGeofences() {
        Log.d(TAG, "removeGeofences(): hit")
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mGeofencingClient.removeGeofences(geofencePendingIntent)
                .addOnCompleteListener(this)
    }


    override fun onComplete(task: Task<Void>) {
        Log.d(TAG, "onComplete(): hit")
        // mPendingGeofenceTask = PendingGeofenceTask.NONE;
        if (task.isSuccessful) {
//               updateGeofencesAdded(!getGeofencesAdded());
            //   setButtonsEnabledState();

            //     int messageId = getGeofencesAdded() ? R.string.geofences_added :
            //            R.string.geofences_removed;
            //Toast.makeText(getActivity(), getString(messageId), Toast.LENGTH_SHORT).show();
//            Toast.makeText(mContext, mContext.getString(R.string.geofences_added), Toast.LENGTH_SHORT).show()
            Toast.makeText(mContext, "Geofence Added or Removed", Toast.LENGTH_LONG).show()
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            //  String errorMessage = GeofenceErrorMessages.getErrorString(this, task.getException());
            // Log.w(TAG, errorMessage);
        }
    }

    private fun checkPermissions(): Boolean {
        val permissonState = ActivityCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
        return permissonState == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
        private val TAG = Geofencing::class.simpleName
    }


}
