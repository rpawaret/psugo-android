package com.ipoondev.android.psugo.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.services.AuthService
import com.ipoondev.android.psugo.signup.CreateUserActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login_create_user.setOnClickListener {
            val createUserIntent =  Intent(this, CreateUserActivity::class.java)
            startActivity(createUserIntent)
        }

        button_login_user.setOnClickListener {
            logIn()
        }
    }

    private fun logIn() {
        val email = edit_login_email.text.toString()
        val password = edit_login_password.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            AuthService.signIn(email, password) { complete ->
                if (complete) {
                    finish()
                } else {
                    Toast.makeText(this, "email หรือ password ไม่ถูกต้อง", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "โปรดกรอก email และ password", Toast.LENGTH_LONG).show()
            return
        }

    }

}
