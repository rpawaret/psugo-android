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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.model.Item
import com.ipoondev.android.psugo.utilities.BROADCAST_GEOFENCE_TRANSITION_ENTER
import com.ipoondev.android.psugo.utilities.BROADCAST_REGISTER_GEOFENCE_COMPLELE

class MapsFragment : Fragment(), OnMapReadyCallback {
    private val TAG = MapsFragment::class.simpleName
    private lateinit var mMap: GoogleMap
    private lateinit var mMapFragment: MapFragment
    lateinit var mFirestore: FirebaseFirestore
    lateinit var playerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirestore = FirebaseFirestore.getInstance()
        playerId = FirebaseAuth.getInstance().currentUser!!.uid

//        Log.d(TAG, "onCreate() : $missionId")

//        val itemList = getAllItems(missionId)

//        checkNotNull(getCurrentMissionId()).apply {
//            getAllItems(this)
//        }

        // GET all items from Mission
//        val missionRef = mFirestore.collection("missions").document(currentMissionId)

        LocalBroadcastManager.getInstance(activity!!).registerReceiver(registerGeofenceCompleteReceiver,
                IntentFilter(BROADCAST_REGISTER_GEOFENCE_COMPLELE))

        LocalBroadcastManager.getInstance(activity!!).registerReceiver(geofenceTransitionEnterReceiver,
                IntentFilter(BROADCAST_GEOFENCE_TRANSITION_ENTER))
    }

    private val registerGeofenceCompleteReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
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
        Log.d(TAG, "onMapReady() : hit")
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }

        val uiSettings = mMap.uiSettings
        uiSettings.isCompassEnabled = true
        uiSettings.isZoomControlsEnabled = true

        getItemList()

    }

    // GET currentMissionId
    private fun getItemList(): ArrayList<Item> {
        var itemList = ArrayList<Item>()
        var currentMissionId: String? = null

        val playerRef = mFirestore.collection("players").document(playerId)
        playerRef.get().addOnCompleteListener { task ->
           if (task.isSuccessful) {
                 currentMissionId = task.result.data["currentMissionId"].toString()
                Log.d(TAG, "$currentMissionId")
                itemList = getAllItems(currentMissionId!!)
           }

        }.addOnFailureListener {

                }
        return itemList
    }

    private fun getAllItems(missionId: String): ArrayList<Item> {
        val itemList = ArrayList<Item>()

        val itemsRef = mFirestore.collection("missions").document(missionId)
                .collection("items")
        itemsRef.get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (itemDocument in task.result) {
                            Log.d(TAG, "${itemDocument.id} => ${itemDocument.data}")
                            val item = itemDocument.toObject(Item::class.java)
                            itemList.add(item)
                            displayMarker(itemList)
                        }
                    } else {
                        Log.d(TAG, "Error getting document: ${task.exception}")
                    }
                }

        return itemList
    }

    private fun displayMarker(itemList: ArrayList<Item>) {

        itemList.forEach { item ->
            val latLng = LatLng(item.geoPoint!!.latitude, item.geoPoint!!.longitude)
            mMap.addMarker(MarkerOptions().
                    position(latLng)
                    .title(item.name))
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))
        }


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(itemList[0].geoPoint!!.latitude,
                        itemList[0].geoPoint!!.longitude), 15F))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity!!.fragmentManager.beginTransaction().remove(mMapFragment).commit()
    }
}
