package com.ipoondev.android.psugo.mission

import android.graphics.Color
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

class MissionDetailActivity : AppCompatActivity() {
    val TAG = MissionDetailActivity::class.simpleName
    var isPlaying = false
    lateinit var missionId: String
    lateinit var playerId: String
    lateinit var mMission: Mission
    lateinit var itemList: ArrayList<Item>

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(BUTTON_STATE_KEY, isPlaying)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_details)
        setSupportActionBar(toolbar_mission_detail)

//        if (savedInstanceState != null) {
//            isPlaying = savedInstanceState.getBoolean(BUTTON_STATE_KEY)
//        }

        playerId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        missionId = intent.extras.getString(EXTRA_MISSION_ID)
                ?: throw IllegalArgumentException("Must pass extra $EXTRA_MISSION_ID")

        fetchMissionById(missionId)
//        initialMissionDetailView(mission)
        initialButtonState()


//        button_play.setOnClickListener {
//            if (!isPlaying) {
//                playMission()
//            } else {
//                stopMission()
//            }
//        }

        button_play.setOnClickListener {
            playMission()
        }
    }

    private fun fetchMissionById(missionId: String) {
        var mission: Mission? = null
        FirebaseFirestore.getInstance().collection("missions").document(missionId)
                .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        mission = documentSnapshot.toObject(Mission::class.java)
                        initialMissionDetailView(mission!!)
                        Log.d(TAG, "Current data: " + documentSnapshot.data);
                    }
                }
    }

    private fun initialMissionDetailView(mission: Mission) {
        toolbar_mission_detail.title = mission.name
        text_mission_detail_detail.text = mission.statement
        text_mission_detail_subject.text = "Categories: ${mission.categories}"
        text_mission_detail_owner.text = "Teacher: Admin"
        text_mission_detail_items.text = mission.selectedItems.toString()
    }

    private fun initialButtonState() {
        FirebaseFirestore.getInstance().collection("players").document(playerId)
                .collection("myMissions").document(missionId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val myMission = documentSnapshot.toObject(MyMission::class.java)
                    if (myMission?.state.equals("Playing")) {
                        button_play.text = myMission?.state ?: "Play"
                        button_play.setBackgroundColor(Color.rgb(81, 180, 109))
                    } else {
                        button_play.text = myMission?.state ?: "Play"
                    }
                }
    }

    private fun getItemNameList(itemList: ArrayList<Item>): String {
        var itemName = ""
        for (item in itemList) {
            itemName += item.name
        }
        return itemName
    }

    private fun fetchSelectedItems() {
        itemList = ArrayList()
        mMission!!.selectedItems?.forEach { itemId ->
            FirebaseFirestore.getInstance().collection("items").document(itemId)
                    .get()
                    .addOnSuccessListener {
                        val item = it.toObject(Item::class.java)!!
                        itemList.add(item)
                    }
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val doc = it.result
                            if (doc.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + doc.data);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        }
                    }
        }

    }

    private fun registerItemsToGeofencing() {
        var mission: Mission? = null

        FirebaseFirestore.getInstance().collection("missions").document(missionId)
                .get()
                .addOnSuccessListener{ documentSnapshot ->
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        mission = documentSnapshot.toObject(Mission::class.java)
                        Log.d(TAG, "Current data: " + documentSnapshot.data)

                        val geofencing = Geofencing(this)
                        mission!!.selectedItems?.forEach { itemId ->
                            FirebaseFirestore.getInstance().collection("items").document(itemId)
                                    .get()
                                    .addOnSuccessListener {
                                        val item = it.toObject(Item::class.java)!!
                                        Log.d(TAG, "ITEM:${item.name}")

                                        geofencing.populateGeofenceList(item)
                                    }
                        }

//                        geofencing.performPendingGeofenceTask(Geofencing.PendingGeofenceTask.ADD)

                    }
                }

//
//        val geofencing = Geofencing(this, itemList)
//        geofencing.performPendingGeofenceTask(Geofencing.PendingGeofenceTask.ADD)
    }

    private fun unRegisterItemsToGeofencing() {

        var mission: Mission? = null
        FirebaseFirestore.getInstance().collection("missions").document(missionId)
                .get()
                .addOnSuccessListener{ documentSnapshot ->
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        mission = documentSnapshot.toObject(Mission::class.java)
                        initialMissionDetailView(mission!!)
                        Log.d(TAG, "Current data: " + documentSnapshot.data);
                    }
                }

        itemList = ArrayList()
        mission!!.selectedItems?.forEach { itemId ->
            FirebaseFirestore.getInstance().collection("items").document(itemId)
                    .get()
                    .addOnSuccessListener {
                        val item = it.toObject(Item::class.java)!!
                        itemList.add(item)
                    }
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val doc = it.result
                            if (doc.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + doc.data);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        }
                    }
        }

//        val geofencing = Geofencing(this, itemList)
//        geofencing.performPendingGeofenceTask(Geofencing.PendingGeofenceTask.REMOVE)
    }

    fun playMission() {
//        FirebaseFirestore.getInstance().collection("missions").document(missionId)
//                .collection("items")
//                .get()
//                .addOnCompleteListener { task ->
//                    val itemList = ArrayList<Item>()
//                    for (document in task.result) {
//                        val item = document.toObject(Item::class.java)
//                        itemList.add(item)
//                    }
//                    val geofencing = Geofencing(this, itemList)
//                    geofencing.performPendingGeofenceTask(Geofencing.PendingGeofenceTask.ADD)
//                }
//        fetchSelectedItems()
        updatePlayerData("playMission")
        registerItemsToGeofencing()

    }

    fun stopMission() {
        updatePlayerData("stopMission")
//        unRegisterItemsToGeofencing()

    }

    private fun updatePlayerData(action: String) {
        if (action == "playMission") {

            val batch = FirebaseFirestore.getInstance().batch()
            // set the new document in myMissions subcollection
            val myMissionsRef = FirebaseFirestore.getInstance()
                    .collection("players").document(playerId)
                    .collection("myMissions").document(missionId)
            batch.set(myMissionsRef, MyMission(missionId, 0, "Playing"))

            // update the currentMissionId field in playerId document to missionId
            val playerIdRef = FirebaseFirestore.getInstance()
                    .collection("players").document(playerId)
            batch.update(playerIdRef, "currentMissionId", missionId)

            // commit
            batch.commit()
                    .addOnCompleteListener {
                        Log.d(TAG, "ADD myMission Document and UPDATE currentMissionId successful")
                        // set button.text = playing when user playing
                        isPlaying = true
                        button_play.text = "Playing"
                        button_play.setBackgroundColor(Color.rgb(81, 180, 109))
                    }.addOnFailureListener {
                        Log.d(TAG, "ADD and UPDATE failed")
                    }
        }

        if (action == "stopMission") {

            val batch = FirebaseFirestore.getInstance().batch()

            // update mission state to Pause
            val myMissionsRef = FirebaseFirestore.getInstance()
                    .collection("players").document(playerId)
                    .collection("myMissions").document(missionId)
            batch.delete(myMissionsRef)
//            batch.update(myMissionsRef, "state", "Paused")

            // update the currentMissionId field in playerId document to null
            val playerIdRef = FirebaseFirestore.getInstance()
                    .collection("players").document(playerId)
            batch.update(playerIdRef, "currentMissionId", null)

            batch.commit()
                    .addOnCompleteListener {
                        Log.d(TAG, "Delete myMission document and UPDATE currentMissionId to null successful")
                        isPlaying = false
                        button_play.text = "Play"
//                        button_play.setBackgroundColor(Color.rgb(224, 171, 24))

                    }.addOnFailureListener {
                        Log.d(TAG, "UPDATE failed")
                    }
        }

    }

    companion object {
        val EXTRA_MISSION_ID = "key_mission_id"
        val BUTTON_STATE_KEY = "button_state_key"
    }
}
