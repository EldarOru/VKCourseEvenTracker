package com.example.eventracker.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.eventracker.data.GeneralRepository
import com.google.firebase.auth.FirebaseUser

class AddEventFragmentViewModel(application: Application): AndroidViewModel(application) {
    private var generalRepository: GeneralRepository? = null
    private var userLiveData: MutableLiveData<FirebaseUser>? = null

    init {
        generalRepository = GeneralRepository(application)
        userLiveData = generalRepository?.getUserLiveData()
    }

    fun createNewEvent(eventName: String, eventDescription: String){
        generalRepository?.createNewEvent(eventName, eventDescription)
    }
}