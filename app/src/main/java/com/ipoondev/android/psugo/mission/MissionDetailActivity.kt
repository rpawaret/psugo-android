package com.ipoondev.android.psugo.mission

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.geofencing.Geofencing
import com.ipoondev.android.psugo.model.Item
import com.ipoondev.android.psugo.model.Mission
import com.ipoondev.android.psugo.model.MyMission
import kotlinx.android.synthetic.main.activity_mission_details.*
import java.util.*


class MissionDetailActivity : AppCompatActivity() {
    val TAG = MissionDetailActivity::class.simpleName
    var isStart = false
    private var geofencing: Geofencing? = null
    lateinit var mFirestore: FirebaseFirestore
    lateinit var missionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_details)
        setSupportActionBar(toolbar_mission_detail)

        geofencing = Geofencing(this)

        missionId = intent.extras.getString(EXTRA_MISSION_ID)
                ?: throw IllegalArgumentException("Must pass extra $EXTRA_MISSION_ID")

        mFirestore = FirebaseFirestore.getInstance()

        val missionRef = mFirestore.collection("missions").document(missionId)

        missionRef.get().addOnSuccessListener { documentSnapshot ->
            val mission = documentSnapshot.toObject(Mission::class.java)
            toolbar_mission_detail.title = mission.name
            text_mission_detail_detail.text = mission.detail
            text_mission_detail_subject.text = "Subject: ${mission.subject}"
            text_mission_detail_owner.text = "Teacher: ${mission.ownerName}"

        }.addOnFailureListener { exception ->
                    Log.e(TAG, exception.localizedMessage)
                }

        val itemsRef = mFirestore.collection("missions").document(missionId).collection("items")
        itemsRef.get().addOnCompleteListener { task ->
            val items = ArrayList<Item>()
            if (task.isSuccessful) {
                for (document in task.result) {
//                    Log.d(TAG, "${document.id} ${document.data}")
                    val item = document.toObject(Item::class.java)
                    items.add(item)
                    text_mission_detail_items.text = "${document.data}"
                }

            } else {
                Log.d(TAG, "Error getting documents: ", task.exception);
            }
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

        val playerId = FirebaseAuth.getInstance().currentUser!!.uid
        val batch = FirebaseFirestore.getInstance().batch()

        // TODO set the new document in myMissions subcollection
        val myMissionsRef = FirebaseFirestore.getInstance()
                .collection("players").document(playerId)
                .collection("myMissions").document(missionId)
        batch.set(myMissionsRef, MyMission(missionId, 0, "Playing"))

        // TODO update the currentMissionId field in playerId document
        val playerIdRef = FirebaseFirestore.getInstance()
                .collection("players").document(playerId)

        batch.update(playerIdRef, "currentMissionId", missionId)

        // TODO commit
        batch.commit()
                .addOnCompleteListener {
                    Log.d(TAG, "Add myMission Document and update currentMissionId successful")
                }.addOnFailureListener {
                    Log.d(TAG, "ADD and UPDATE failed")
                }
    }

    fun stopMission() {
        isStart = false

        // TODO unregister geofences
//        GeofencingService.unRegisterAllGeofences(this)
//        geofencing?.performPendingGeofenceTask("REMOVE")
        // TODO remove item's marker
    }


    companion object {
        val EXTRA_MISSION_ID = "key_mission_id"
    }
}
