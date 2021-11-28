package com.example.eventracker.domain

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser

interface GeneralRepository {
    suspend fun login(email: String, password: String)
    suspend fun register(name: String, email: String, password: String)
    //TODO DELETE?
    suspend fun getUserFromFirebase()
    suspend fun createEvent(event: Event)
    suspend fun deleteEvent(event: Event)

    fun getFirebaseUser(): LiveData<FirebaseUser>
    fun getUser(): LiveData<User>
}