package com.example.eventracker.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.domain.usecases.GetUserAccUseCase
import com.example.eventracker.domain.usecases.RegistrationUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]+"

    private val generalRepositoryImpl: GeneralRepositoryImpl = GeneralRepositoryImpl(application)
    private val registrationUserCase = RegistrationUseCase(generalRepositoryImpl)
    private val getUserAccUseCase = GetUserAccUseCase(generalRepositoryImpl)
    private var userLiveData: LiveData<FirebaseUser> = getUserAccUseCase.getUserAcc()
    var shouldCloseScreen: MutableLiveData<Unit>? = null

    private val _errorName = MutableLiveData<Boolean>()
    val errorName: LiveData<Boolean>
        get() = _errorName

    private val _errorEmail = MutableLiveData<Boolean>()
    val errorEmail: LiveData<Boolean>
        get() = _errorEmail

    private val _errorPassword = MutableLiveData<Boolean>()
    val errorPassword: LiveData<Boolean>
        get() = _errorPassword

    private val _errorRepeatPassword = MutableLiveData<Boolean>()
    val errorRepeatPassword: LiveData<Boolean>
        get() = _errorRepeatPassword
//TODO что-то сделать с закрытием
    init {
        shouldCloseScreen = generalRepositoryImpl.getPop()
    }

    fun register(name: String, email: String, password: String, repeatPassword: String){
        val validateData = validateInput(name, email, password, repeatPassword)
        if (validateData) {
            viewModelScope.launch(Dispatchers.Main) {
                registrationUserCase.register(name, email, password)
            }
        }
    }

    fun getUserLiveData(): LiveData<FirebaseUser>? {
        return userLiveData
    }

    fun checkInput(name: String, email: String, password: String, repeatPassword: String): Boolean{
        return email.matches(emailPattern.toRegex()) &&
                password.length > 2 && name.length > 2 &&
                password == repeatPassword
    }

    private fun validateInput(name: String, email: String, password: String, repeatPassword: String): Boolean{
        var flag = true
        if (name.isBlank()){
            _errorName.value = true
            flag = false
        }
        if (email.isBlank()){
            _errorEmail.value = true
            flag = false
        }
        if (password.isBlank()){
            _errorPassword.value = true
            flag = false
        }
        if (repeatPassword.isBlank() || repeatPassword != password){
            _errorRepeatPassword.value = true
            flag = false
        }
        return flag
    }

    fun resetErrorName(){
        _errorName.value = false
    }

    fun resetErrorEmail(){
        _errorEmail.value = false
    }

    fun resetErrorPassword(){
        _errorPassword.value = false
    }

    fun resetErrorRepeatPassword(){
        _errorRepeatPassword.value = false
    }
}