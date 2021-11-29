package com.example.eventracker.domain.usecases

import androidx.lifecycle.LiveData
import com.example.eventracker.domain.GeneralRepository
import com.example.eventracker.domain.models.User

class GetUserDatabaseUseCase(private val generalRepository: GeneralRepository) {
    fun getUser(): LiveData<User>{
        return generalRepository.getUser()
    }
}