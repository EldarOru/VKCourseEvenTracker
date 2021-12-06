package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.GeneralRepository
import com.example.eventracker.domain.models.Event

class AddInviteToEventsUseCase(private val generalRepository: GeneralRepository) {
    suspend fun addInviteToEvents(event: Event){
        generalRepository.addInviteToEvents(event)
    }
}