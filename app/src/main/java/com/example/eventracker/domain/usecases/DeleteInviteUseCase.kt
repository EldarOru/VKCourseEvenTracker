package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.GeneralRepository
import com.example.eventracker.domain.models.Event

class DeleteInviteUseCase(private val generalRepository: GeneralRepository) {
    suspend fun deleteInvite(event: Event){
        generalRepository.deleteInvite(event)
    }
}