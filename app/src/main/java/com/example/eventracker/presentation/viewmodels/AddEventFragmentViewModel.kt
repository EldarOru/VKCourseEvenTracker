package com.example.eventracker.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.domain.Event
import com.example.eventracker.domain.usecases.CreateEventUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddEventFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val generalRepositoryImpl: GeneralRepositoryImpl = GeneralRepositoryImpl(application)
    private val createEventUseCase = CreateEventUseCase(generalRepositoryImpl)


    fun createNewEvent(eventName: String, eventDescription: String, date: String) {
        viewModelScope.launch(Dispatchers.Main) {
            createEventUseCase.createEvent(Event(name = eventName, description = eventDescription, date = date))
        }
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