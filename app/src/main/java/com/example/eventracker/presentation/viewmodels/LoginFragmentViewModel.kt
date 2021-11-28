package com.example.eventracker.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.domain.usecases.GetUserAccUseCase
import com.example.eventracker.domain.usecases.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val generalRepositoryImpl: GeneralRepositoryImpl = GeneralRepositoryImpl(application)
    private val loginUseCase = LoginUseCase(generalRepositoryImpl)
    private val getUserAccUseCase = GetUserAccUseCase(generalRepositoryImpl)
    private val userLiveData: LiveData<FirebaseUser> = getUserAccUseCase.getUserAcc()

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
                loginUseCase.login(email, password)
            }
        }
    }

    fun getUserLiveData(): LiveData<FirebaseUser>? {
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

