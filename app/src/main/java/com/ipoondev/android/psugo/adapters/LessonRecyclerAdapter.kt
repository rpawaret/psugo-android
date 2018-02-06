package com.ipoondev.android.psugo.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ipoondev.android.coderswag.model.Category
import com.ipoondev.android.psugo.R

class LessonRecyclerAdapter(val context: Context, val categories: List<Category>) : RecyclerView.Adapter<LessonRecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.lesson_list_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bindCategory(categories[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val categoryImage = itemView?.findViewById<ImageView>(R.id.image_lesson)
        val categoryName = itemView?.findViewById<TextView>(R.id.text_lesson_name)

        fun bindCategory(category: Category, context: Context) {
            val resourceId = context.resources.getIdentifier(category.image, "drawable", context.packageName)
            categoryImage?.setImageResource(resourceId)
            categoryName?.text = category.title
        }

    }

}