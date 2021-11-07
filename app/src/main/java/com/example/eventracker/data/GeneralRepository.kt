package com.example.eventracker.data

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import io.reactivex.Completable
import androidx.lifecycle.MutableLiveData

import com.google.firebase.auth.FirebaseUser




class GeneralRepository(private val application: Context?) {
     var firebaseAuth: FirebaseAuth? = null
    private var userLiveData: MutableLiveData<FirebaseUser>? = null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null

    init {
        firebaseAuth = FirebaseAuth.getInstance()
        userLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()

        if (firebaseAuth?.currentUser != null) {
            //userLiveData?.value = firebaseAuth?.currentUser
            loggedOutLiveData?.value = false
        }
    }

    fun login(email: String, password: String){
        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    userLiveData?.value = firebaseAuth?.currentUser
                }
                else{
                    Toast.makeText(application,"Login failure: ${it.exception?.localizedMessage}", Toast.LENGTH_SHORT ).show()

            }
        }
    }

    fun register(email: String, password: String){
        firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(application,"Success", Toast.LENGTH_SHORT ).show()
                }
                else Toast.makeText(application,"Login failure: ${it.exception?.localizedMessage}", Toast.LENGTH_SHORT ).show()


        }
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
        return loggedOutLiveData
    }
}