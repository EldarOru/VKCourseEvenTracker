package com.example.eventracker.data

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth

import androidx.lifecycle.MutableLiveData
import com.example.eventracker.domain.Event
import com.example.eventracker.domain.GeneralRepository
import com.example.eventracker.domain.User

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.*

class GeneralRepositoryImpl(private val application: Application): GeneralRepository {
    //TODO GOD OBJECT, CHANGE ARCHITECTURE
    private var firebaseAuth: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    private var firebaseUserLiveData: MutableLiveData<FirebaseUser>? = null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null

    //вся инфа
    private var userLiveDatabase: MutableLiveData<User>? = null

    //нужно ли делать popback
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
            loggedOutLiveData?.value = false
            GlobalScope.launch {
                getUserFromFirebase()
            }
        }
    }

    fun getPop(): MutableLiveData<Unit>?{
        return shouldPopBackStack
    }

    //TODO как передвинуть ошибки от самого firebase я не знаю
    override suspend fun login(email: String, password: String): Unit = withContext(Dispatchers.IO){
        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
            if (it.isSuccessful) {
                firebaseUserLiveData?.value = firebaseAuth?.currentUser
            }
            else{
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        application.applicationContext,
                        "Login failure: ${it.exception?.localizedMessage} ${Thread.currentThread()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override suspend fun register(name: String, email: String, password: String){
        firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful){
                    addToDatabase(name, email)
                    shouldPopBackStack?.value = Unit
                }
                else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(
                            application.applicationContext,
                            "Login failure: ${it.exception?.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
        return loggedOutLiveData
    }

    fun logOut() {
        firebaseAuth!!.signOut()
        loggedOutLiveData!!.postValue(true)
    }

    override suspend fun createEvent(event: Event): Unit = withContext(Dispatchers.IO){
        val key = database?.push()?.key.toString()
        database?.child("users")?.child(firebaseAuth?.currentUser!!.uid)?.child("events")
            ?.child(key)
            ?.setValue(Event(key,userLiveDatabase?.value!!.login, event.date, event.name, event.description))
    }

    override suspend fun getUserFromFirebase(): Unit = withContext(Dispatchers.IO){
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
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(application.applicationContext,"Failure: ${error.message}",
                    Toast.LENGTH_SHORT ).show()
            }
        })
    }

    override suspend fun deleteEvent(event: Event): Unit = withContext(Dispatchers.IO){
        database?.child("users")?.child(firebaseAuth?.currentUser!!.uid)
            ?.child("events")?.child(event.key)?.removeValue()
    }

    override fun getUser(): LiveData<User>{
        return userLiveDatabase!!
    }

    override fun getFirebaseUser(): LiveData<FirebaseUser> {
        return firebaseUserLiveData!!
    }
}