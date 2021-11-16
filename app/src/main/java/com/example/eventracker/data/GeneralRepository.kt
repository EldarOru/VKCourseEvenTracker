package com.example.eventracker.data

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth

import androidx.lifecycle.MutableLiveData
import com.example.eventracker.domain.Event
import com.example.eventracker.domain.User

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.util.*

class GeneralRepository(private val application: Application) {
    private var firebaseAuth: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    private var firebaseUserLiveData: MutableLiveData<FirebaseUser>? = null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null
    private var userLiveDatabase: MutableLiveData<User>? = null
    private var shouldPopBackStack: MutableLiveData<Unit>? = null
    init {
        shouldPopBackStack = MutableLiveData()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUserLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()
        userLiveDatabase = MutableLiveData()
        database = FirebaseDatabase
            .getInstance("https://eventracker-c501a-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference
        if (firebaseAuth?.currentUser != null) {
            firebaseUserLiveData?.value = firebaseAuth?.currentUser
            getUserFromDatabase()
            loggedOutLiveData?.value = false
        }
    }

    fun getPop(): MutableLiveData<Unit>?{
        return shouldPopBackStack
    }
    fun login(email: String, password: String){
        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    firebaseUserLiveData?.value = firebaseAuth?.currentUser
                }
                else{
                    Toast.makeText(application.applicationContext,
                        "Login failure: ${it.exception?.localizedMessage}",
                        Toast.LENGTH_SHORT ).show()
            }
        }
    }

    fun register(name: String, email: String, password: String){
        firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(application.applicationContext,"Success", Toast.LENGTH_SHORT ).show()
                    addToDatabase(name, email)
                    shouldPopBackStack?.value = Unit
                }
                else Toast.makeText(application.applicationContext,
                    "Login failure: ${it.exception?.localizedMessage}",
                    Toast.LENGTH_SHORT ).show()
        }
    }

    private fun addToDatabase(name: String, email: String){
        database?.child("users")?.child(firebaseAuth?.currentUser!!.uid)?.setValue(User(
            name, email
        ))
    }

    //TODO
    fun checkName(name: String){
        database?.child("users")?.orderByChild("names")?.equalTo(name)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
        return firebaseUserLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
        return loggedOutLiveData
    }

    fun logOut() {
        firebaseAuth!!.signOut()
        loggedOutLiveData!!.postValue(true)
    }

    fun createNewEvent(eventName: String, eventDescription: String, date: String){
        val key = database?.push()?.key.toString()
        database?.child("users")?.child(firebaseAuth?.currentUser!!.uid)?.child("events")
                //TODO CHANGE KEY
            ?.child(key)
            ?.setValue(Event(key,userLiveDatabase?.value!!.login, date, eventName, eventDescription))

    }

    //TODO CHANGE THREAD
    private fun getUserFromDatabase(){
        var user: User
        database?.child("users")?.child(firebaseAuth?.currentUser!!.uid)
            ?.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayEvent = arrayListOf<Event>()
                for (snapshotOne in snapshot.child("events").children){
                    arrayEvent.add(Event(creator = snapshotOne.child("creator").value.toString(),
                    name = snapshotOne.child("name").value.toString(),
                    description = snapshotOne.child("description").value.toString(),
                    date = snapshotOne.child("date").value.toString(),
                    key = snapshotOne.child("key").value.toString()))
                }
                user = User(login = snapshot.child("login").value.toString(),
                email = snapshot.child("email").value.toString(),
                listOfEvents = arrayEvent)
                userLiveDatabase?.value = user
                Log.d("KEK",userLiveDatabase!!.value!!.login)

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(application.applicationContext,"Failure: ${error.message}",
                    Toast.LENGTH_SHORT ).show()
            }
        })
    }

    fun deleteEvent(event: Event){
        database?.child("users")?.child(firebaseAuth?.currentUser!!.uid)
            ?.child("events")?.child(event.key)?.removeValue()
    }

    fun getUserLiveDatabase(): MutableLiveData<User>?{
        return userLiveDatabase
    }
}