package com.example.eventracker.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventracker.domain.models.Event
import com.example.eventracker.domain.usecases.CreateEventUseCase
import com.example.eventracker.domain.usecases.GetFirebaseInfoUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapFragmentViewModel(
    private val createEventUseCase: CreateEventUseCase,
    private val getFirebaseInfoUseCase: GetFirebaseInfoUseCase
): ViewModel(){

    fun createNewEvent(eventName: String, eventDescription: String, date: String, eventPosition: LatLng, eventTime: String) {
            viewModelScope.launch(Dispatchers.Main) {
                createEventUseCase.createEvent(
                    Event(
                        name = eventName,
                        description = eventDescription,
                        date = date,
                        eventPosition = eventPosition,
                        time = eventTime
                    )
                )
            }
        }
    }