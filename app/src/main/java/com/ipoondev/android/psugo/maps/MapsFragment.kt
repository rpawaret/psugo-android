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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.auth.AuthUiActivity
import com.ipoondev.android.psugo.geofencing.Geofencing
import com.ipoondev.android.psugo.model.Item

class MapsFragment : Fragment(), OnMapReadyCallback {
    private val TAG = MapsFragment::class.simpleName
    private lateinit var mMap: GoogleMap
    private lateinit var mMapFragment: MapFragment
    lateinit var mFirestore: FirebaseFirestore
    var playerId: String? = null
    lateinit var geofencing: Geofencing
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirestore = FirebaseFirestore.getInstance()
        // เมื่อ logout แล้วทำให้ playerID เป็น null -> app crash
        playerId = FirebaseAuth.getInstance().currentUser?.uid

        mContext = activity as Context

//        LocalBroadcastManager.getInstance(activity!!).registerReceiver(registerGeofenceCompleteReceiver,
//                IntentFilter(BROADCAST_REGISTER_GEOFENCE_COMPLELE))


    }
//
//    private val registerGeofenceCompleteReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            Toast.makeText(activity, "Receive register geofence complete signal", Toast.LENGTH_LONG).show()
//            Log.d(TAG, "Receive register geofence complete signal")
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMapFragment = activity!!.fragmentManager.findFragmentById(R.id.map) as MapFragment
        mMapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
//        Log.d(TAG, "onMapReady() : hit")
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }

        val uiSettings = mMap.uiSettings
        uiSettings.isCompassEnabled = true
        uiSettings.isZoomControlsEnabled = true

//        checkExistingUser { isNull ->
//            if (isNull.not()) {
//                getItemList()
//            }
//        }
        if (hasPlayerId()) {
            getItemList()
        } else {
            showRequestSignInDialog {ok ->
                if (ok) {
                    startAuthUiActivity()
                }
            }
        }
//
//        LocalBroadcastManager.getInstance(activity!! ).registerReceiver(geofenceTransitionEnterReceiver,
//                IntentFilter(BROADCAST_GEOFENCE_TRANSITION_ENTER))

    }

//    private val geofenceTransitionEnterReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
////            Toast.makeText(activity, "Receive geofence transition ENTER signal", Toast.LENGTH_LONG).show()
//            Log.d(TAG, "Receive geofence transition ENTER signal")
//
//            showTransitionEnterDialog(context!!)
////            showTransitionEnterDialog()
//        }
//    }

//    private fun showTransitionEnterDialog(context: Context) {
//        Log.d(TAG, "showTransitionEnterDialog() : hit")
////        val builder = AlertDialog.Builder((activity as? Context)!!)
//        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.myDialog))
//                .setMessage("Do you want to take the quiz?")
//                .setPositiveButton("Quiz", { dialog, which ->
//                    startQuizActivity()
//                })
//                .setNegativeButton("NO", { dialog, which -> dialog.cancel() })
//
//        val dialog = builder.create()
//        dialog.show()
//    }
//
//    private fun startQuizActivity() {
//        val quizInent = Intent(activity as Context, QuizActivity::class.java)
//        startActivity(quizInent)
//    }

    private fun hasPlayerId() : Boolean {
        return playerId != null
    }

//    private fun checkExistingUser(isNull: (Boolean) -> Unit) {
//        Log.d(TAG, "checkExistingUser() : hit")
//        val user = FirebaseAuth.getInstance().currentUser
//        if (user == null) {
//            Log.d(TAG, "checkExistingUser() : if user is null")
//            showRequestSignInDialog { ok ->
//                if (ok) {
//                    startAuthUiActivity()
//                }
//                isNull(true)
//            }
//        } else {
//            Log.d(TAG, "checkExistingUser() : If user is not null")
//            isNull(false)
//            playerId = user.uid
//        }
//    }

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

    // GET currentMissionId
    private fun getItemList(): ArrayList<Item> {
        var itemList = ArrayList<Item>()
        var currentMissionId: String? = null

        val playerRef = mFirestore.collection("players").document(playerId!!)
        playerRef.get().addOnCompleteListener { task ->
           if (task.isSuccessful) {
                 currentMissionId = task.result.data!!["currentMissionId"].toString()
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
//                            Log.d(TAG, "${itemDocument.id} => ${itemDocument.data}")
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

//        itemList.forEach { item ->
//            val latLng = LatLng(item.geoPoint!!.latitude, item.geoPoint!!.longitude)
//            mMap.addMarker(MarkerOptions().
//                    position(latLng)
//                    .title(item.name))
//        }

//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                LatLng(itemList[0].geoPoint!!.latitude,
//                        itemList[0].geoPoint!!.longitude), 15F))

    }

    override fun onDestroyView() {
        super.onDestroyView()
            activity!!.fragmentManager.beginTransaction().remove(mMapFragment).commit()
    }
}
