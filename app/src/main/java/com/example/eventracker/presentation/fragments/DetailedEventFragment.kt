package com.example.eventracker.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.R
import com.example.eventracker.databinding.ActivityMainBinding.inflate
import com.example.eventracker.databinding.DetailedEventFragmentBinding
import com.example.eventracker.databinding.MainEventFragmentBinding
import com.example.eventracker.domain.models.Event
import com.example.eventracker.presentation.viewmodels.DetailedEventFragmentViewModel
import com.example.eventracker.presentation.viewmodels.MainFragmentViewModel
import com.example.eventracker.presentation.viewmodels.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailedEventFragment: Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var detailedEventFragmentViewModel: DetailedEventFragmentViewModel
    private var detailedEventFragmentBinding: DetailedEventFragmentBinding? = null
    private var eventKey: String = Event.UNDEFINED_KEY
    private var eL = MutableLiveData<LatLng>()
    private var mode = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailedEventFragmentBinding = DetailedEventFragmentBinding.inflate(inflater, container, false)
        return detailedEventFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailedEventFragmentViewModel = ViewModelProvider(this, ViewModelFactory())[DetailedEventFragmentViewModel::class.java]
        initMap()
        getArgs()

        detailedEventFragmentViewModel.getUserLiveDatabase().observe(viewLifecycleOwner){
            setInfo()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        eL.observe(viewLifecycleOwner) {
            mMap.addMarker(MarkerOptions().position(it).title("Location"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 17f))
        }
    }

    private fun setInfo(){
        detailedEventFragmentViewModel.getEventByKey(mode, eventKey)
        val event = detailedEventFragmentViewModel.event.value
            detailedEventFragmentBinding?.apply {
            detailedNameEventTV.text = event?.name
            detailedDateEventTV.text = event?.date
            detailedDescriptionEventTV.text = event?.description
                eL.value = event!!.eventPosition
        }

    }

    private fun initMap(){
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.detailed_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getArgs(){
        eventKey = requireArguments().getString(EVENT_KEY) as String
        mode = requireArguments().getString(MODE_KEY) as String
    }

    companion object{
        fun newInstanceDetailedEventFragment(mode: String, eventKey: String): DetailedEventFragment{
            return DetailedEventFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE_KEY, mode)
                    putString(EVENT_KEY, eventKey)
                }
            }
        }

        private const val MODE_KEY = "modeKey"
        private const val EVENT_KEY = "eventID"
    }
}