package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.GeneralRepository
import com.example.eventracker.domain.models.Event
class GetEventByKeyUseCase(private val generalRepository: GeneralRepository) {
    fun getEventByKey(mode: String, key: String): Event{
        return generalRepository.getEventByKey(mode = mode, key = key)
    }
}