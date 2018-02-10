package com.ipoondev.android.psugo.lessons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.geofencing.Geofencing
import com.ipoondev.android.psugo.services.DataService
import com.ipoondev.android.psugo.utilities.EXTRA_LESSON_ID
import kotlinx.android.synthetic.main.activity_lessons_detail.*

class LessonsDetailActivity : AppCompatActivity() {
    val TAG = LessonsDetailActivity::class.simpleName
    var isEnroll = false
    private var geofencing: Geofencing? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons_detail)
        geofencing = Geofencing(this)

        val lessonId = intent.getIntExtra(EXTRA_LESSON_ID, 0)
        text_lesson_detail_title.text = DataService.lessons.get(0).title

        button_enroll.setOnClickListener {

            if (!isEnroll) {
                enrollLesson()
                button_enroll.text = "Enrolled"
            } else {
                unEnrollLesson()
                button_enroll.text = "Enroll"
            }
        }

    }

    fun enrollLesson() {
        isEnroll = true
        Toast.makeText(this, "You enroll lesson Complete", Toast.LENGTH_LONG).show()

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

    fun unEnrollLesson() {
        isEnroll = false
        Toast.makeText(this, "You unenroll lesson Complete", Toast.LENGTH_LONG).show()

        // TODO unregister geofences
//        GeofencingService.unRegisterAllGeofences(this)
        geofencing?.performPendingGeofenceTask("REMOVE")
        // TODO remove item's marker
    }


}
