package com.ipoondev.android.psugo.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestoreException
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.model.Mission
import kotlinx.android.synthetic.main.mission_list_item.view.*

class MissionRecyclerAdapter(options: FirestoreRecyclerOptions<Mission>) : FirestoreRecyclerAdapter<Mission, MissionRecyclerAdapter.MissionsHolder>(options) {
    private val TAG = MissionRecyclerAdapter::class.simpleName
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MissionsHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.mission_list_item, parent, false)
        return MissionsHolder(view)
    }

    override fun onBindViewHolder(holder: MissionsHolder, position: Int, mission: Mission) {
        holder.bindMissions(mission)
    }

    override fun onError(e: FirebaseFirestoreException) {
        super.onError(e)
        Log.d(TAG, "onError: ${e.localizedMessage}")
    }

    inner class MissionsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val missionName = itemView.findViewById<TextView>(R.id.text_mission_name)

        fun bindMissions(mission: Mission) {
            itemView.text_mission_name?.text = mission.title
        }
    }

}