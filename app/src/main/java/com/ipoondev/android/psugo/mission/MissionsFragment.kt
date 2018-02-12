package com.ipoondev.android.psugo.mission

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.adapters.MissionsRecyclerAdapter
import com.ipoondev.android.psugo.model.Mission
import kotlinx.android.synthetic.main.fragment_missions.*

class MissionsFragment : Fragment() {

    lateinit var adapter: MissionsRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_missions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = FirebaseFirestore.getInstance()
                .collection("missions")

        val options = FirestoreRecyclerOptions.Builder<Mission>()
                .setQuery(query, Mission::class.java)
                .build()

        adapter = MissionsRecyclerAdapter(options)
        adapter.notifyDataSetChanged()
        recycler_missions.setHasFixedSize(true)
        recycler_missions.layoutManager = LinearLayoutManager(activity)
        recycler_missions.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}

