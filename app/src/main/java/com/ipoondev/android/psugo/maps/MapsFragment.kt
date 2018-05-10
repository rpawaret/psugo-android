package com.ipoondev.android.psugo.maps

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.auth.AuthUiActivity
import com.ipoondev.android.psugo.model.Item
import com.ipoondev.android.psugo.model.Mission
import com.ipoondev.android.psugo.model.Player

class MapsFragment : Fragment(), OnMapReadyCallback {
    private val TAG = MapsFragment::class.simpleName
    private lateinit var mMap: GoogleMap
    private lateinit var mMapFragment: MapFragment
    var playerId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerId = FirebaseAuth.getInstance().currentUser?.uid
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

        if (hasPlayerId()) {
            displayMarkers()
        } else {
            showRequestSignInDialog { ok ->
                if (ok) {
                    startAuthUiActivity()
                }
            }
        }
    }

    private fun displayMarkers() {
        FirebaseFirestore.getInstance().collection("players").document(playerId!!)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val document = it.result
                        if (document.exists()) {
//                            Log.d(TAG, "DocumentSnapshot data: " + document.data)
                        }
                    }
                }
                .addOnSuccessListener {
                    val player = it.toObject(Player::class.java)
                    Log.d(TAG, "currentMissionId: ${player!!.currentMissionId}")
                    if (player.currentMissionId != null) {
                        FirebaseFirestore.getInstance().collection("missions").document(player.currentMissionId!!)
                                .get()
                                .addOnSuccessListener {
                                    val mission = it.toObject(Mission::class.java)
                                    Log.d(TAG, "selectedItems: ${mission!!.selectedItems}")

                                    mission.selectedItems!!.forEach { itemId ->
                                        FirebaseFirestore.getInstance().collection("items").document(itemId)
                                                .get()
                                                .addOnSuccessListener {
                                                    val item = it.toObject(Item::class.java)!!
//                                                Log.d(TAG, "Item Name: ${item!!.name}")
//                                                Log.d(TAG, "Item Latitude: ${item.latitude}")
//                                                Log.d(TAG, "Item Longitude: ${item.longitude}")
                                                    displayMarker(item)
                                                }
                                    }

                                }
                    }
                }
                .addOnFailureListener {
                }
    }

    private fun displayMarker(item: Item) {
        val latitude = item.latitude!!.toDouble()
        val longitude = item.longitude!!.toDouble()
        val name = item.name
        val latLng = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(latLng).title(name))
    }

    private fun hasPlayerId(): Boolean {
        return playerId != null
    }

    private fun showRequestSignInDialog(complete: (Boolean) -> Unit) {
        val builder = AlertDialog.Builder(activity as Context)
                .setMessage("Please Sign in First")
                .setPositiveButton("OK", { dialog, which ->
                    dialog.dismiss()
                    complete(true)
                })
        val dialog = builder.create()
        dialog.show()
    }

    private fun startAuthUiActivity() {
        val authUiActivityIntent = Intent(activity, AuthUiActivity::class.java)
        startActivity(authUiActivityIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity!!.fragmentManager.beginTransaction().remove(mMapFragment).commit()
    }
}
