package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.GeneralRepository

class RegistrationUseCase(private val generalRepository: GeneralRepository) {
    suspend fun register(name: String, email: String, password: String){
        generalRepository.register(name, email, password)
    }
}