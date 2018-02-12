package com.ipoondev.android.psugo.mission

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipoondev.android.psugo.R
import kotlinx.android.synthetic.main.fragment_filter_dialog.*

class FilterDialogFragment : DialogFragment() {

    private var mRootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_filter_dialog, container, false)

        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        button_search.setOnClickListener {
            onSearchClicked()
        }

        button_cancel.setOnClickListener {
            onCancelClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun onSearchClicked() {

    }

    fun onCancelClicked() {
        dismiss()
    }

    companion object {
        val TAG = FilterDialogFragment::class.simpleName
    }

}
