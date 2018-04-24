package com.ipoondev.android.psugo.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.model.Mission
import kotlinx.android.synthetic.main.mission_list_item.view.*

class MissionRecyclerAdapter(options: FirestoreRecyclerOptions<Mission>, val itemClick: (DocumentSnapshot) -> Unit)
    : FirestoreRecyclerAdapter<Mission, MissionRecyclerAdapter.MissionsHolder>(options) {

    private val TAG = MissionRecyclerAdapter::class.simpleName
    lateinit var documentSnapshot: DocumentSnapshot

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mission_list_item, parent, false)
        return MissionsHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: MissionsHolder, position: Int, mission: Mission) {
        documentSnapshot = snapshots.getSnapshot(position)
        holder.bindMissions(mission, documentSnapshot)
    }

    override fun onError(e: FirebaseFirestoreException) {
        super.onError(e)
        Log.d(TAG, "onError: ${e.localizedMessage}")
    }

    inner class MissionsHolder(itemView: View, val itemClick: (DocumentSnapshot) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bindMissions(mission: Mission, documentSnapshot: DocumentSnapshot) {
            itemView.text_mission_name?.text = mission.name
            itemView.text_mission_subject.text = mission.categories
            itemView.text_mission_owner.text = "Admin"
            itemView.text_mission_num_player.text = "0 Players"

            itemView.setOnClickListener {
                itemClick(documentSnapshot)
            }
        }
    }
}