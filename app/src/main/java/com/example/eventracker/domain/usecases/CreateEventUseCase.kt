package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.models.Event
import com.example.eventracker.domain.GeneralRepository

class CreateEventUseCase(private val generalRepository: GeneralRepository) {
    suspend fun createEvent(event: Event){
        generalRepository.createEvent(event)
    }
}