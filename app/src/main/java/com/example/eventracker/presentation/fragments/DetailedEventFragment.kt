package com.example.eventracker.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.databinding.ActivityMainBinding.inflate
import com.example.eventracker.databinding.DetailedEventFragmentBinding
import com.example.eventracker.databinding.MainEventFragmentBinding
import com.example.eventracker.domain.models.Event
import com.example.eventracker.presentation.viewmodels.DetailedEventFragmentViewModel
import com.example.eventracker.presentation.viewmodels.MainFragmentViewModel
import com.example.eventracker.presentation.viewmodels.ViewModelFactory

class DetailedEventFragment: Fragment() {
    private lateinit var detailedEventFragmentViewModel: DetailedEventFragmentViewModel
    private var detailedEventFragmentBinding: DetailedEventFragmentBinding? = null
    private var eventKey: String = Event.UNDEFINED_KEY

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
        getArgs()
        detailedEventFragmentViewModel.getUserLiveDatabase().observe(viewLifecycleOwner){
            setInfo()
        }
    }

    private fun setInfo(){
        detailedEventFragmentViewModel.getEventByKey(eventKey)
        val event = detailedEventFragmentViewModel.event.value
        Log.d("MYEVENT", event.toString())
            detailedEventFragmentBinding?.apply {
            detailedNameEventTV.text = event?.name
            detailedDateEventTV.text = event?.date
            detailedDescriptionEventTV.text = event?.description
        }

    }

    private fun getArgs(){
        eventKey = requireArguments().getString(EVENT_KEY) as String
        Log.d("KEY", eventKey)
    }

    companion object{
        fun newInstanceDetailedEventFragment(eventKey: String): DetailedEventFragment{
            return DetailedEventFragment().apply {
                arguments = Bundle().apply {
                    putString(EVENT_KEY, eventKey)
                }
            }
        }

        private const val EVENT_KEY = "eventID"
    }
}