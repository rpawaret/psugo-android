package com.ipoondev.android.psugo.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.utilities.EXTRA_CATEGORY

class LessonsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons_detail)

        val categoryType = intent.getStringExtra(EXTRA_CATEGORY)
        println(categoryType)

    }
}
