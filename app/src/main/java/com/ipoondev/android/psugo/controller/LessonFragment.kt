package com.ipoondev.android.psugo.controller

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.adapters.LessonRecyclerAdapter
import com.ipoondev.android.psugo.services.DataService
import kotlinx.android.synthetic.main.fragment_lessons.*

class LessonFragment : Fragment() {

    lateinit var adapter : LessonRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_lessons, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LessonRecyclerAdapter(activity, DataService.categories)
        recycler_lesson.adapter = adapter
        recycler_lesson.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        recycler_lesson.layoutManager = layoutManager



    }



}

