package com.ipoondev.android.psugo.mission

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.model.Item
import com.ipoondev.android.psugo.model.Mission
import com.ipoondev.android.psugo.model.MyMission
import kotlinx.android.synthetic.main.activity_mission_details.*
import java.util.*


class MissionDetailActivity : AppCompatActivity() {
    val TAG = MissionDetailActivity::class.simpleName
    var isPlaying = false
    lateinit var mFirestore: FirebaseFirestore
    lateinit var missionId: String
    lateinit var playerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_details)
        setSupportActionBar(toolbar_mission_detail)
        playerId = FirebaseAuth.getInstance().currentUser!!.uid

        missionId = intent.extras.getString(EXTRA_MISSION_ID)
                ?: throw IllegalArgumentException("Must pass extra $EXTRA_MISSION_ID")

        mFirestore = FirebaseFirestore.getInstance()

        val missionRef = mFirestore.collection("missions").document(missionId)
        missionRef.get()
                .addOnSuccessListener { documentSnapshot ->
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

        val myMissionRef = mFirestore
                .collection("players").document(playerId)
                .collection("myMissions").document(missionId)
        myMissionRef.get()
                .addOnCompleteListener {
                    checkNotNull(it).apply {
                        Log.d(TAG, "DocumentSnapshot data: " + this.result.data)
                        val myMission = this.result.toObject(MyMission::class.java)
                        button_play.text = myMission.state
                    }
                }.addOnFailureListener { e ->
                    Log.d(TAG, "Error getting Document: ${e.localizedMessage}")

                }


        button_play.setOnClickListener {

            if (!isPlaying) {
                playMission()
            } else {
                pauseMission()
            }
        }

    }

    fun playMission() {

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
                }.addOnFailureListener {
                    Log.d(TAG, "ADD and UPDATE failed")
                }
    }

    fun pauseMission() {

        val batch = FirebaseFirestore.getInstance().batch()

        // update mission state to Pause
        val myMissionsRef = FirebaseFirestore.getInstance()
                .collection("players").document(playerId)
                .collection("myMissions").document(missionId)
        batch.update(myMissionsRef, "state", "Paused")

        // update the currentMissionId field in playerId document to null
        val playerIdRef = FirebaseFirestore.getInstance()
                .collection("players").document(playerId)
        batch.update(playerIdRef, "currentMissionId", null)

        batch.commit()
                .addOnCompleteListener {
                    Log.d(TAG, "UPDATE myMission state to Pause and UPDATE currentMissionId to null successful")
                    isPlaying = false
                    button_play.text = "Paused"

                }.addOnFailureListener {
                    Log.d(TAG, "UPDATE failed")
                }

    }


    companion object {
        val EXTRA_MISSION_ID = "key_mission_id"
    }
}
