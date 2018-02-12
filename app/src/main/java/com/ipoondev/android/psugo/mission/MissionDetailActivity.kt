package com.ipoondev.android.psugo.mission

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.geofencing.Geofencing
import com.ipoondev.android.psugo.model.Mission
import kotlinx.android.synthetic.main.activity_mission_details.*

class MissionDetailActivity : AppCompatActivity() {
    val TAG = MissionDetailActivity::class.simpleName
    var isStart = false
    private var geofencing: Geofencing? = null
    lateinit var mFirestore: FirebaseFirestore
    lateinit var mMission: Mission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_details)
        setSupportActionBar(toolbar_mission_detail)

        geofencing = Geofencing(this)

        val missionId = intent.extras.getString(EXTRA_MISSION_ID)
                ?: throw IllegalArgumentException("Must pass extra $EXTRA_MISSION_ID")

        mFirestore = FirebaseFirestore.getInstance()

        val missionRef= mFirestore.collection("missions").document(missionId)

        missionRef.get().addOnSuccessListener{ documentSnapshot ->
            val mission = documentSnapshot.toObject(Mission::class.java)
            toolbar_mission_detail.title = mission.name
            text_mission_detail_owner.text = mission.ownerName

        }.addOnFailureListener { exception ->
                    Log.e(TAG, exception.localizedMessage)
                }

        button_start.setOnClickListener {

            if (!isStart) {
                startMission()
                button_start.text = "Started"
            } else {
                stopMission()
                button_start.text = "start"
            }
        }

    }

    fun startMission() {
        isStart = true
        Toast.makeText(this, "You start play mission", Toast.LENGTH_LONG).show()

//        geofencing!!.populateGeofenceList(DataService.items2)

//        geofencing?.performPendingGeofenceTask("ADD")

        // TODO if register geofence successful then create Marker and display it on map
//        if (GeofencingService.isRegisterComplete) {
//            // TODO send notify to MapsFragment for display item's marker
//            val registerGeofenceComplete = Intent(BROADCAST_REGISTER_GEOFENCE_COMPLELE)
//            val isComplete = LocalBroadcastManager.getInstance(this).sendBroadcast(registerGeofenceComplete)
//            Log.d(TAG, "send register complete signal to MapsFragment is Complete: $isComplete")
//        }

    }

    fun stopMission() {
        isStart = false
        Toast.makeText(this, "You stop play mission Complete", Toast.LENGTH_LONG).show()

        // TODO unregister geofences
//        GeofencingService.unRegisterAllGeofences(this)
        geofencing?.performPendingGeofenceTask("REMOVE")
        // TODO remove item's marker
    }


    companion object {
        val EXTRA_MISSION_ID = "key_mission_id"
    }
}
