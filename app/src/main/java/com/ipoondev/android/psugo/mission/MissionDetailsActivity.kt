package com.ipoondev.android.psugo.mission

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.geofencing.Geofencing
import com.ipoondev.android.psugo.services.DataService
import com.ipoondev.android.psugo.utilities.EXTRA_MISSION_ID
import kotlinx.android.synthetic.main.activity_mission_details.*

class MissionDetailsActivity : AppCompatActivity() {
    val TAG = MissionDetailsActivity::class.simpleName
    var isStart = false
    private var geofencing: Geofencing? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_details)
        geofencing = Geofencing(this)

        val lessonId = intent.getIntExtra(EXTRA_MISSION_ID, 0)
        text_mission_detail_title.text = DataService.missions.get(0).title

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

//        GeofencingService.createGeofenceList(DataService.items1)
        geofencing!!.populateGeofenceList(DataService.items2)

//        GeofencingService.registerAllGeofences(this)
        geofencing?.performPendingGeofenceTask("ADD")

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


}
