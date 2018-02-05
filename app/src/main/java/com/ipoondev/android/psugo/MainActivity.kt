package com.ipoondev.android.psugo

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lessons-> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_maps-> {
                pushFragment(MapsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile-> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    fun pushFragment(fragment: Fragment?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction?.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

    }
}
