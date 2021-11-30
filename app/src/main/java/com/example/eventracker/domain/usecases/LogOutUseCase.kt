package com.example.eventracker.domain.usecases

import com.example.eventracker.domain.GeneralRepository

class LogOutUseCase(private val generalRepository: GeneralRepository) {
    fun logOut(){
        generalRepository.logOut()
    }
}