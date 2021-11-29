package com.example.eventracker.domain.usecases

import androidx.lifecycle.LiveData
import com.example.eventracker.domain.GeneralRepository

class GetFirebaseInfoUseCase(private val generalRepository: GeneralRepository) {
    fun getFirebaseInfo(): LiveData<String>{
        return generalRepository.getFirebaseInfo()
    }
}