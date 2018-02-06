package com.ipoondev.android.psugo.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.model.Lesson

class LessonsRecyclerAdapter(val context: Context, val lessons: List<Lesson>, val itemClick: (Lesson) -> Unit) : RecyclerView.Adapter<LessonsRecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.lesson_list_item, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return lessons.count()
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bindLesson(lessons[position], context)
    }

    inner class Holder(itemView: View?, val itemClick: (Lesson) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val lessonImage = itemView?.findViewById<ImageView>(R.id.image_lesson)
        val lessonName = itemView?.findViewById<TextView>(R.id.text_lesson_name)

        fun bindLesson(lesson: Lesson, context: Context) {
            val resourceId = context.resources.getIdentifier(lesson.image, "drawable", context.packageName)
            lessonImage?.setImageResource(resourceId)
            lessonName?.text = lesson.title

            itemView.setOnClickListener { itemClick(lesson) }
        }

    }

}