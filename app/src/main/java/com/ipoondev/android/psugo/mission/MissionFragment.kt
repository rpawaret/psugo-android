package com.ipoondev.android.psugo.mission

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.adapters.MissionRecyclerAdapter
import com.ipoondev.android.psugo.model.Mission
import kotlinx.android.synthetic.main.fragment_missions.*

class MissionFragment : Fragment() {

    lateinit var adapter: MissionRecyclerAdapter
    lateinit var mFilterDialog: FilterDialogFragment

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

        adapter = MissionRecyclerAdapter(options) { documentSnapshot ->
            Log.d(TAG, "missionId: ${documentSnapshot.id}")
            val intent = Intent(activity, MissionDetailActivity::class.java)
            intent.putExtra(MissionDetailActivity.EXTRA_MISSION_ID, documentSnapshot.id)
            startActivity(intent)
        }
        adapter.notifyDataSetChanged()
        recycler_missions.setHasFixedSize(true)
        recycler_missions.layoutManager = LinearLayoutManager(activity)
        recycler_missions.adapter = adapter

        filter_bar.setOnClickListener {
            onFilterClicked()
        }

        mFilterDialog = FilterDialogFragment()
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun onFilterClicked() {
        mFilterDialog.show(activity?.supportFragmentManager, FilterDialogFragment.TAG)
    }

    companion object {
        val TAG = MissionFragment::class.simpleName
    }

}

