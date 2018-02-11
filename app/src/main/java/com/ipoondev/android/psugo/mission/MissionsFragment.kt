package com.ipoondev.android.psugo.mission

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.adapters.MissionsRecyclerAdapter
import com.ipoondev.android.psugo.services.DataService
import com.ipoondev.android.psugo.utilities.EXTRA_LESSON_ID
import kotlinx.android.synthetic.main.fragment_missions.*

class MissionsFragment : Fragment() {

    lateinit var mAdapter: MissionsRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_missions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = MissionsRecyclerAdapter(activity!!, DataService.lessons) { lesson ->
            val lessonDetailIntent = Intent(activity, MissionDetailsActivity::class.java)
            lessonDetailIntent.putExtra(EXTRA_LESSON_ID, lesson.id)
            startActivity(lessonDetailIntent)

        }

        recycler_missions.adapter = mAdapter
        recycler_missions.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        recycler_missions.layoutManager = layoutManager

    }

}

