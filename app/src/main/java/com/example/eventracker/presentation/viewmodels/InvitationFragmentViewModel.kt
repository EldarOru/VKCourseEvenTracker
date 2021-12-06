package com.example.eventracker.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventracker.domain.models.Event
import com.example.eventracker.domain.models.User
import com.example.eventracker.domain.usecases.*
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//TODO всё изменить
class InvitationFragmentViewModel(private val getUserDatabaseUseCase: GetUserDatabaseUseCase,
                                  private val getUserAccUseCase: GetUserAccUseCase,
                                  private val logOutUseCase: LogOutUseCase,
                                  private val deleteInviteUseCase: DeleteInviteUseCase,
                                  private val addInviteToEventsUseCase: AddInviteToEventsUseCase): ViewModel() {

    private var userLiveDatabase: LiveData<User> = getUserDatabaseUseCase.getUser()

    private val userLiveData: LiveData<FirebaseUser> = getUserAccUseCase.getUserAcc()

    fun logOut(){
        logOutUseCase.logOut()
    }

    fun getUserLiveDatabase(): LiveData<User> {
        return userLiveDatabase
    }

    fun getUserLiveData(): LiveData<FirebaseUser> {
        return userLiveData
    }

    fun deleteInvite(event: Event){
        viewModelScope.launch(Dispatchers.Main) {
            deleteInviteUseCase.deleteInvite(event)
        }
    }

    fun addInviteToEventsUseCase(event: Event){
        viewModelScope.launch(Dispatchers.Main) {
            addInviteToEventsUseCase.addInviteToEvents(event)
        }
    }
}