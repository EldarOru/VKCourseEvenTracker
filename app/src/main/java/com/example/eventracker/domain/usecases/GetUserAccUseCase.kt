package com.example.eventracker.domain.usecases

import androidx.lifecycle.LiveData
import com.example.eventracker.domain.GeneralRepository
import com.google.firebase.auth.FirebaseUser

class GetUserAccUseCase(private val generalRepository: GeneralRepository) {
    fun getUserAcc(): LiveData<FirebaseUser>{
        return generalRepository.getFirebaseUser()
    }
}