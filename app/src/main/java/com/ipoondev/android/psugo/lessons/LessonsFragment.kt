package com.ipoondev.android.psugo.lessons

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.adapters.LessonsRecyclerAdapter
import com.ipoondev.android.psugo.services.DataService
import com.ipoondev.android.psugo.utilities.EXTRA_LESSON_ID
import kotlinx.android.synthetic.main.fragment_lessons.*

class LessonsFragment : Fragment() {

    lateinit var mAdapter: LessonsRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lessons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = LessonsRecyclerAdapter(activity, DataService.lessons) { lesson ->
            val lessonDetailIntent = Intent(activity, LessonsDetailActivity::class.java)
            lessonDetailIntent.putExtra(EXTRA_LESSON_ID, lesson.id)
            startActivity(lessonDetailIntent)

        }

        recycler_lessons.adapter = mAdapter
        recycler_lessons.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        recycler_lessons.layoutManager = layoutManager

    }

}

