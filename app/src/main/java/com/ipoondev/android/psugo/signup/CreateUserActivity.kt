package com.ipoondev.android.psugo.signup

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.services.AuthService
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {
    private val TAG = CreateUserActivity::class.simpleName
    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]" // ใช้ format กับ ios

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        image_create_avatar.setOnClickListener {
            generateUserAvatar()
        }

        button_create_background_avatar.setOnClickListener {
            generateAvatarColor()
        }

        button_create_user.setOnClickListener {
            signUp()
        }

    }

    private fun generateUserAvatar() {
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if (color == 0) {
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        image_create_avatar.setImageResource(resourceId)
    }

    private fun generateAvatarColor() {
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        image_create_avatar.setBackgroundColor(Color.rgb(r, g, b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB], 1"
    }

    private fun signUp() {
        val email = edit_create_email.text.toString()
        val password = edit_create_password.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            AuthService.signUp(email, password) { complete ->
                if (complete) {
                    finish()

                }
            }
        } else {
            Toast.makeText(this, "โปรดกรอก email และ password", Toast.LENGTH_LONG).show()
            return
        }

    }


}
