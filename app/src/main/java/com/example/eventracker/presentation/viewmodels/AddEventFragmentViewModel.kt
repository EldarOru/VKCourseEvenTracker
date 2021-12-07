package com.example.eventracker.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventracker.domain.usecases.CreateEventUseCase
import com.example.eventracker.domain.usecases.GetFirebaseInfoUseCase
import java.text.SimpleDateFormat
import java.util.*

class AddEventFragmentViewModel(
    private val createEventUseCase: CreateEventUseCase,
    private val getFirebaseInfoUseCase: GetFirebaseInfoUseCase): ViewModel() {

    private val firebaseInfoLiveData = getFirebaseInfoUseCase.getFirebaseInfo()

    private val _errorName = MutableLiveData<Boolean>()
    val errorName: LiveData<Boolean>
        get() = _errorName

    private val _errorDescription = MutableLiveData<Boolean>()
    val errorDescription: LiveData<Boolean>
        get() = _errorDescription

    /*
    fun createNewEvent(eventName: String, eventDescription: String, date: String) {
        val validateData = validateInput(eventName, eventDescription)
        if (validateData) {
            viewModelScope.launch(Dispatchers.Main) {
                createEventUseCase.createEvent(
                    Event(
                        name = eventName,
                        description = eventDescription,
                        date = date
                    )
                )
            }
        }
    }
     */

    fun validateInput(name: String, description: String): Boolean {
        var flag = true
        if (name.isBlank()) {
            _errorName.value = true
            flag = false
        }
        if (description.isBlank()) {
            _errorDescription.value = true
            flag = false
        }
        return flag
    }

    //TODO CHANGE EVERYTHING
    fun checkDate(s: String): String{
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        val gregorianCalendar = GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

        return if (s == "Click here to choose date")
            dateFormat.format(gregorianCalendar.time)
        else s
    }

    fun checkTime(s: String): String{
        val dateFormat = SimpleDateFormat("HH:mm")

        return if (s == "Click here to choose time")
            dateFormat.format(Date())
        else s
    }

    fun resetErrorName(){
        _errorName.value = false
    }

    fun resetErrorDescription(){
        _errorDescription.value = false
    }

    fun getFirebaseInfoLiveData(): LiveData<String>{
        return firebaseInfoLiveData
    }
}