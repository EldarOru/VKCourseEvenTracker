package com.example.eventracker.presentation.viewmodels

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseUser

import com.example.eventracker.domain.usecases.GetFirebaseInfoUseCase
import com.example.eventracker.domain.usecases.GetUserAccUseCase
import com.example.eventracker.domain.usecases.LogInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragmentViewModel(
    private val logInUseCase: LogInUseCase,
    private val getUserAccUseCase: GetUserAccUseCase,
    private val getFirebaseInfoUseCase: GetFirebaseInfoUseCase): ViewModel(){

    private val userLiveData: LiveData<FirebaseUser> = getUserAccUseCase.getUserAcc()

    private val firebaseInfoLiveData = getFirebaseInfoUseCase.getFirebaseInfo()

    private val _errorEmail = MutableLiveData<Boolean>()
    val errorEmail: LiveData<Boolean>
        get() = _errorEmail

    private val _errorPassword = MutableLiveData<Boolean>()
    val errorPassword: LiveData<Boolean>
        get() = _errorPassword



    fun login(email: String, password: String){
        val validateData = validateInput(email, password)
        if (validateData) {
            viewModelScope.launch(Dispatchers.Main) {
                logInUseCase.login(email, password)
            }
        }
    }

    fun getFirebaseInfoLiveData(): LiveData<String>{
        return firebaseInfoLiveData
    }

    fun getUserLiveData(): LiveData<FirebaseUser>{
        return userLiveData
    }

    private fun validateInput(email: String, password: String): Boolean{
        var flag = true
        if (email.isBlank()){
            _errorEmail.value = true
            flag = false
        }
        if (password.isBlank()){
            _errorPassword.value = true
            flag = false
        }
        return flag
    }

    fun resetErrorEmail(){
        _errorEmail.value = false
    }

    fun resetErrorPassword(){
        _errorPassword.value = false
    }
}

