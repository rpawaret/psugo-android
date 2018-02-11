package com.ipoondev.android.psugo.settings

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.utilities.PERMISSION_REQUEST_FINE_LOCATION
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        checkbox_location_permissions.setOnClickListener {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_FINE_LOCATION)
        }

    }

    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            checkbox_location_permissions.isChecked = false
        } else {
            checkbox_location_permissions.isChecked = true
            checkbox_location_permissions.isEnabled = false
        }
    }
}
