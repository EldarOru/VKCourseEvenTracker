package com.example.eventracker.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.eventracker.data.GeneralRepository
import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat
import java.util.*

class AddEventFragmentViewModel(application: Application): AndroidViewModel(application) {
    private var generalRepository: GeneralRepository? = null
    private var userLiveData: MutableLiveData<FirebaseUser>? = null

    init {
        generalRepository = GeneralRepository(application)
        userLiveData = generalRepository?.getUserLiveData()
    }

    fun createNewEvent(eventName: String, eventDescription: String, date: String){
        generalRepository?.createNewEvent(eventName, eventDescription, date)
    }

    fun checkInput(eventName: String, eventDescription: String): Boolean{
        return eventName.length > 1 && eventDescription.length > 1
    }

    //TODO
    fun checkDate(s: String): String{
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        val gregorianCalendar = GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

        return if (s == "Click here to choose date")
            dateFormat.format(gregorianCalendar.time)
        else s
    }
}