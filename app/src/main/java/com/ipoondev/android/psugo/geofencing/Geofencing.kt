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
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.model.Item


class Geofencing(private val mContext: Context) : OnCompleteListener<Void> {
    private val mGeofencingClient: GeofencingClient
    private val mGeofenceList: ArrayList<Geofence>
    private val mGeofencePendingIntent: PendingIntent?

    init {
        mGeofenceList = ArrayList()
        mGeofencePendingIntent = null
        mGeofencingClient = LocationServices.getGeofencingClient(mContext)
    }

    private val geofencingRequest: GeofencingRequest
        get() {
            val builder = GeofencingRequest.Builder()
            builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            builder.addGeofences(mGeofenceList)
            return builder.build()
        }

//     We use FLAG_UPDATE_CURRENT so that we get the same pending intent back
// when calling addGeofences() and removeGeofences().
    private val geofencePendingIntent: PendingIntent
        get() {
            if (mGeofencePendingIntent != null) {
                return mGeofencePendingIntent
            }
            val intent = Intent(mContext, GeofenceTransitionsIntentService::class.java)
            return PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }



    fun populateGeofenceList(items: List<Item>) {
        Log.d(TAG, "populateGeofenceList(): hit")

        for (item in items) {
            val geofence = Geofence.Builder()
                    .setRequestId(item.address)
                    .setExpirationDuration((item.timeout * 60 * 60 * 1000))
                    .setCircularRegion(item.geoPoint.latitude, item.geoPoint.longitude, item.radius)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build()
            mGeofenceList.add(geofence)
        }

        Log.d(TAG, mGeofenceList.toString())
    }


    fun performPendingGeofenceTask(perform: String) {
        Log.d(TAG, "performPendingGeofenceTask(): hit")
        //if (mPendingGeofenceTask == PendingGeofenceTask.ADD) {
        when(perform) {
            "ADD" -> addGeofences()
            "REMOVE" -> removeGeofences()
            else -> Log.e("Geofencing", "Could not Perform")
        }
        // } else if (mPendingGeofenceTask == PendingGeofenceTask.REMOVE) {
        //    removeGeofences();
        //}
    }

    private fun addGeofences() {
        Log.d(TAG, "addGeofences(): hit")
        //  if (!checkPermissions()) {
        //     showSnackbar(getString(R.string.insufficient_permissions));
        //     return;
        // }

        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mGeofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)
                .addOnCompleteListener(this)
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
            //   updateGeofencesAdded(!getGeofencesAdded());
            //   setButtonsEnabledState();

            //     int messageId = getGeofencesAdded() ? R.string.geofences_added :
            //            R.string.geofences_removed;
            //Toast.makeText(getActivity(), getString(messageId), Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, mContext.getString(R.string.geofences_added), Toast.LENGTH_SHORT).show()
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            //  String errorMessage = GeofenceErrorMessages.getErrorString(this, task.getException());
            // Log.w(TAG, errorMessage);
        }
    }

    companion object {

        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
        private val TAG = Geofencing::class.simpleName
    }


}
