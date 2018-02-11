package com.ipoondev.android.psugo.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.model.Mission

class MissionsRecyclerAdapter(val context: Context, val missions: List<Mission>, val itemClick: (Mission) -> Unit) : RecyclerView.Adapter<MissionsRecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.mission_list_item, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return missions.count()
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bindMission(missions[position], context)
    }

    inner class Holder(itemView: View?, val itemClick: (Mission) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val missionImage = itemView?.findViewById<ImageView>(R.id.image_mission)
        val missionName = itemView?.findViewById<TextView>(R.id.text_mission_name)

        fun bindMission(mission: Mission, context: Context) {
            val resourceId = context.resources.getIdentifier(mission.image, "drawable", context.packageName)
            missionImage?.setImageResource(resourceId)
            missionName?.text = mission.title

            itemView.setOnClickListener { itemClick(mission) }
        }

    }

}