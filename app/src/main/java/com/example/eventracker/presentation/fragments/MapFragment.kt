package com.example.eventracker.presentation.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.R
import com.example.eventracker.databinding.AddEventFragmentBinding
import com.example.eventracker.databinding.MapFragmentBinding
import com.example.eventracker.presentation.viewmodels.AddEventFragmentViewModel
import com.example.eventracker.presentation.viewmodels.MapFragmentViewModel
import com.example.eventracker.presentation.viewmodels.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.RuntimeException

class MapFragment: Fragment(), OnMapReadyCallback {

    private var mapFragmentBinding: MapFragmentBinding? = null
    private lateinit var mapFragmentViewModel: MapFragmentViewModel
    private var mLocationPermissionsGranted = false
    private lateinit var mMap: GoogleMap

    private lateinit var onFragmentsInteractionsListener: OnFragmentsInteractionsListener
    private lateinit var mLocationClient: FusedLocationProviderClient

    private var eventName = ""
    private var eventDate = ""
    private var eventDescription = ""
    private var eventPosition: LatLng = LatLng(-33.852, 151.211)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentsInteractionsListener){
            onFragmentsInteractionsListener = context
        }else{
            throw RuntimeException("Activity must implement OnFragmentsInteractionsListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapFragmentBinding = MapFragmentBinding.inflate(inflater, container, false)
        return mapFragmentBinding?.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapFragmentViewModel = ViewModelProvider(this, ViewModelFactory())[MapFragmentViewModel::class.java]
        getParams()
        getLocationPermission()
        initMap()

        mapFragmentBinding?.finishButton?.setOnClickListener {
            Log.d("POSITIONBUTTON", eventPosition.latitude.toString())
            mapFragmentViewModel.createNewEvent(eventName, eventDescription, eventDate, eventPosition)
            onFragmentsInteractionsListener.onPopBackStack()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLocation()
        CameraUpdateFactory.zoomBy(10f)
        mMap.setOnMapClickListener {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it).title("Location"))
            eventPosition = it
            Log.d("POSITION", eventPosition.latitude.toString())
        }
    }

    //почитать как работает
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionsResult: called.")
        mLocationPermissionsGranted = false
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    var i = 0
                    while (i < grantResults.size) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false
                            Log.d(TAG, "onRequestPermissionsResult: permission failed")
                            return
                        }
                        i++
                    }
                    Log.d(MapFragment.TAG, "onRequestPermissionsResult: permission granted")
                    mLocationPermissionsGranted = true
                }
            }
        }
    }

    private fun getLocation() {
        mLocationClient = FusedLocationProviderClient(activity)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                COURSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationClient.lastLocation.addOnCompleteListener {
                if (it.isSuccessful) {
                    val location: Location = it.result
                    eventPosition = LatLng(location.latitude, location.longitude)
                    setStartLocation(location.latitude, location.longitude)
                }
            }
        }
    }

    private fun setStartLocation(latitude: Double, longitude: Double){
        val currentPosition = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(currentPosition).title("You are here"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 17f))
    }

    private fun initMap(){
        if (mLocationPermissionsGranted) {
            val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }

    private fun getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions")
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    COURSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mLocationPermissionsGranted = true
            } else {
                ActivityCompat.requestPermissions(
                    activity as Activity,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } else {
            ActivityCompat.requestPermissions(
                activity as Activity,
                permissions,
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun getParams(){
        val args = requireArguments()
        eventName = args.getString(EVENT_NAME) ?: throw Exception("Smth is wrong")
        eventDescription = args.getString(EVENT_DESCRIPTION) ?: throw Exception("Smth is wrong")
        eventDate = args.getString(EVENT_DATE) ?: throw Exception("Smth is wrong")
    }

    companion object{
        private const val TAG = "MapActivity"

        private const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1234

        private const val EVENT_DATE = "event_date"
        private const val EVENT_NAME = "event_name"
        private const val EVENT_DESCRIPTION = "event_description"

        fun newInstanceMapFragment(date: String,
                                   name: String,
                                   description: String): MapFragment{
            return MapFragment().apply {
                arguments = Bundle().apply {
                    putString(EVENT_DATE, date)
                    putString(EVENT_NAME, name)
                    putString(EVENT_DESCRIPTION, description)
                }
            }
        }
    }

}