package com.ipoondev.android.psugo.maps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.utilities.BROADCAST_GEOFENCE_TRANSITION_ENTER
import com.ipoondev.android.psugo.utilities.BROADCAST_REGISTER_GEOFENCE_COMPLELE

class MapsFragment : Fragment(), OnMapReadyCallback {
    private val TAG = MapsFragment::class.simpleName
    private lateinit var mMap: GoogleMap
    private lateinit var mMapFragment: MapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalBroadcastManager.getInstance(activity!!).registerReceiver(registerGeofenceCompleteReceiver,
                IntentFilter(BROADCAST_REGISTER_GEOFENCE_COMPLELE))

        LocalBroadcastManager.getInstance(activity!!).registerReceiver(geofenceTransitionEnterReceiver,
                IntentFilter(BROADCAST_GEOFENCE_TRANSITION_ENTER))
    }

    private val registerGeofenceCompleteReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // TODO create item's marker then display on map
            Toast.makeText(activity, "Receive register geofence complete signal", Toast.LENGTH_LONG).show()
            Log.d(TAG, "Receive register geofence complete signal")
        }
    }

    private val geofenceTransitionEnterReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
//            Toast.makeText(activity, "Receive geofence transition ENTER signal", Toast.LENGTH_LONG).show()
            Log.d(TAG, "Receive geofence transition ENTER signal")

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMapFragment = activity!!.fragmentManager.findFragmentById(R.id.map) as MapFragment
        mMapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }

        val uiSettings = mMap.uiSettings
        uiSettings.isCompassEnabled = true
        uiSettings.isZoomControlsEnabled = true


    }

    fun displayMarker() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity!!.fragmentManager.beginTransaction().remove(mMapFragment).commit()
    }
}
