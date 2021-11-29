package com.example.eventracker.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.domain.models.Event
import com.example.eventracker.domain.models.User
import com.example.eventracker.domain.usecases.DeleteEventUseCase
import com.example.eventracker.domain.usecases.GetUserAccUseCase
import com.example.eventracker.domain.usecases.GetUserDatabaseUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragmentViewModel:ViewModel() {
    private val generalRepositoryImpl: GeneralRepositoryImpl = GeneralRepositoryImpl()

    private val getUserDatabaseUseCase = GetUserDatabaseUseCase(generalRepositoryImpl)
    private var userLiveDatabase: LiveData<User> = getUserDatabaseUseCase.getUser()

    private val deleteEventUseCase = DeleteEventUseCase(generalRepositoryImpl)

    private val getUserAccUseCase = GetUserAccUseCase(generalRepositoryImpl)
    private val userLiveData: LiveData<FirebaseUser> = getUserAccUseCase.getUserAcc()

    fun logOut(){
        generalRepositoryImpl.logOut()
    }

    /*
    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
        return userLiveData
    }

     */

    fun getUserLiveDatabase(): LiveData<User>{
        return userLiveDatabase
    }

    fun deleteEvent(event: Event){
        viewModelScope.launch(Dispatchers.Main) {
            deleteEventUseCase.deleteEvent(event)
        }
    }
}
