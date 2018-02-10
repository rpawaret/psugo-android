package com.ipoondev.android.psugo.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ipoondev.android.psugo.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login_create_user.setOnClickListener {
            val createUserIntent =  Intent(this, CreateUserActivity::class.java)
            startActivity(createUserIntent)
        }
    }

}
