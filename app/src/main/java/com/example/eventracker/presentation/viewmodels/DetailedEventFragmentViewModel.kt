package com.example.eventracker.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventracker.domain.models.Event
import com.example.eventracker.domain.models.User
import com.example.eventracker.domain.usecases.GetEventByKeyUseCase
import com.example.eventracker.domain.usecases.GetFirebaseInfoUseCase
import com.example.eventracker.domain.usecases.GetUserDatabaseUseCase

class DetailedEventFragmentViewModel(
    private val getUserDatabaseUseCase: GetUserDatabaseUseCase,
    private val getEventByKeyUseCase: GetEventByKeyUseCase): ViewModel() {

    private var userLiveDatabase: LiveData<User> = getUserDatabaseUseCase.getUser()

    var event = MutableLiveData<Event>()

    fun getEventByKey(key: String){
        Log.d("ZAEBAL", userLiveDatabase.value.toString())
        event.value = getEventByKeyUseCase.getEventByKey(key)
    }

    fun getUserLiveDatabase(): LiveData<User>{
        return userLiveDatabase
    }
}