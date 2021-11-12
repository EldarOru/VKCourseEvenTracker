package com.example.eventracker.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.eventracker.data.GeneralRepository
import com.example.eventracker.domain.User
import com.google.firebase.auth.FirebaseUser

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private var generalRepository: GeneralRepository? = null
    private var userLiveData: MutableLiveData<FirebaseUser>? = null
    private var userLiveDatabase: MutableLiveData<User>? = null
    init {
        generalRepository = GeneralRepository(application)
        userLiveData = generalRepository?.getUserLiveData()
        userLiveDatabase = generalRepository?.getUserLiveDatabase()
    }

    fun logOut(){
        generalRepository?.logOut()
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
        return userLiveData
    }

    fun getUserLiveDatabase(): MutableLiveData<User>?{
        return userLiveDatabase
    }
}
