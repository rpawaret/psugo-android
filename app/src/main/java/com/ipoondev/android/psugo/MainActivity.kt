package com.ipoondev.android.psugo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ipoondev.android.psugo.maps.MapsFragment
import com.ipoondev.android.psugo.mission.MissionFragment
import com.ipoondev.android.psugo.profile.ProfileFragment
import com.ipoondev.android.psugo.quiz.QuizActivity
import com.ipoondev.android.psugo.utilities.BROADCAST_GEOFENCE_TRANSITION_ENTER
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
private val TAG = MainActivity::class.simpleName

    lateinit var missionFragment: MissionFragment
    lateinit var mapsFragment: MapsFragment
    lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            missionFragment = MissionFragment()
            mapsFragment = MapsFragment()
            profileFragment = ProfileFragment()
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_missions -> {
                pushFragment(missionFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_maps -> {
                pushFragment(mapsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                pushFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this@MainActivity).registerReceiver(geofenceTransitionEnterReceiver,
                IntentFilter(BROADCAST_GEOFENCE_TRANSITION_ENTER))

    }

    private val geofenceTransitionEnterReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(TAG, "Receive geofence transition ENTER signal")
            showTransitionEnterDialog()
        }
    }

    private fun showTransitionEnterDialog() {
        Log.d(TAG, "showTransitionEnterDialog() : hit")
        val builder = AlertDialog.Builder(this)
                .setMessage("Do you want to take the quiz?")
                .setPositiveButton("Quiz", { dialog, which ->
//                    startQuizActivity()
                })
                .setNegativeButton("NO", { dialog, which -> dialog.cancel() })

        val dialog = builder.create()
        dialog.show()
    }

    private fun startQuizActivity() {
        val quizInent = Intent(this@MainActivity, QuizActivity::class.java)
        startActivity(quizInent)
    }

    private fun pushFragment(fragment: Fragment?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction?.replace(R.id.frame_layout, fragment)?.commit()
    }

}
