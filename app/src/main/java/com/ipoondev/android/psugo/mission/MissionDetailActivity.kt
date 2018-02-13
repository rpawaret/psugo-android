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
            text_mission_detail_subject.text = mission.subject
            text_mission_detail_owner.text = mission.ownerName

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

//        val myMission = HashMap<String, Any>()
//        myMission.put("missionId", missionId)
//        myMission.put("score", 0)
//        myMission.put("state", "Playing")
//
//        FirebaseFirestore.getInstance()
//                .collection("players").document(playerId)
//                .collection("myMissions").document(missionId)
//                .set(myMission)
//                .addOnCompleteListener {
//                    Log.d(TAG, "add complete")
//
//                }.addOnFailureListener {
//                    Log.d(TAG, it.localizedMessage)
//                }

        mFirestore.collection("players")
                .whereEqualTo("playerId", playerId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData())
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", it.getException());
                    }
                }


//        FirebaseFirestore.getInstance().
//                collection("players").document(playerId)
//                .update("score", 2)
//                .addOnCompleteListener {
//                    Log.d(TAG, "Update complete")
//                }.addOnFailureListener {
//                    Log.d(TAG, it.localizedMessage)
//                }
//        FirebaseFirestore.getInstance().collection("").add("document")
//        FirebaseFirestore.getInstance().collection().document().set()

//        playersRef.get()
//                .addOnSuccessListener {documentSnapshot ->
//                    val player = documentSnapshot.toObject(Player::class.java)
//                    Log.d(TAG, "${player.playerName} ${player.playerId} ${player.score}")
//                }

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

        // TODO unregister geofences
//        GeofencingService.unRegisterAllGeofences(this)
//        geofencing?.performPendingGeofenceTask("REMOVE")
        // TODO remove item's marker
    }


    companion object {
        val EXTRA_MISSION_ID = "key_mission_id"
    }
}
