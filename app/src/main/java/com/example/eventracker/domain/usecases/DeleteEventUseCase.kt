package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.Event
import com.example.eventracker.domain.GeneralRepository

class DeleteEventUseCase(private val generalRepository: GeneralRepository) {
    suspend fun deleteEvent(event: Event){
        generalRepository.deleteEvent(event)
    }
}