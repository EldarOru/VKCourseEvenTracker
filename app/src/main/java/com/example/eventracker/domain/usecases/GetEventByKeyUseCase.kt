package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.GeneralRepository
import com.example.eventracker.domain.models.Event
class GetEventByKeyUseCase(private val generalRepository: GeneralRepository) {
    fun getEventByKey(key: String): Event{
        return generalRepository.getEventByKey(key = key)
    }
}