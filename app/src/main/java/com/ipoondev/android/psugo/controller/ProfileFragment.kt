package com.ipoondev.android.psugo.controller

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.services.AuthService
import kotlinx.android.synthetic.main.profile_header_main.*

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_login.setOnClickListener {
            AuthService.loginUser("pawares.r@gmail.com", "123456")
        }
    }






}
