package com.example.eventracker.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth

import androidx.lifecycle.MutableLiveData
import com.example.eventracker.domain.models.Event
import com.example.eventracker.domain.GeneralRepository
import com.example.eventracker.domain.models.User

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.*

class GeneralRepositoryImpl(): GeneralRepository {
    //подключаем авторизацию и базу данных
    private var firebaseAuth: FirebaseAuth? = null
    private var database: DatabaseReference? = null

    //инфа о состоянии юзера
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null

    //инфа о юзере из firebase authentication
    private var firebaseUserLiveData: MutableLiveData<FirebaseUser>? = null

    //инфа о результате выполнения запроса
    private var firebaseInfoLiveData: MutableLiveData<String>? = null

    //вся инфа о юзере из бд
    private var userLiveDatabase: MutableLiveData<User>? = null

    //нужно ли делать popback
    private var shouldPopBackStack: MutableLiveData<Unit>? = null

    private var idListOne = arrayListOf<String>()
    private var user = User()
    init {
        shouldPopBackStack = MutableLiveData()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUserLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()
        userLiveDatabase = MutableLiveData()
        firebaseInfoLiveData = MutableLiveData()
        database = FirebaseDatabase
            .getInstance("https://eventracker-c501a-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference
        if (firebaseAuth?.currentUser != null) {
            firebaseUserLiveData?.value = firebaseAuth?.currentUser
            loggedOutLiveData?.value = false
            getUserFromFirebase()
            getAllUsersID()


        }
    }

    fun getPop(): MutableLiveData<Unit>?{
        return shouldPopBackStack
    }

    override suspend fun login(email: String, password: String): Unit = withContext(Dispatchers.IO){
        Log.d("Kek", "${Thread.currentThread().name}")
        val job = async {
            firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("Kek", "${Thread.currentThread().name}")
                    firebaseUserLiveData?.value = firebaseAuth?.currentUser
                    firebaseInfoLiveData?.value = "Success"
                } else {
                    firebaseInfoLiveData?.value = "Login failure: ${it.exception?.localizedMessage}"
                }
            }
        }
        job.await()
        }

    override suspend fun register(name: String, email: String, password: String): Unit = withContext(Dispatchers.IO){
        firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful){
                    addToDatabase(name, email)
                    shouldPopBackStack?.value = Unit
                    firebaseInfoLiveData?.value = "Success"
                }
                else {
                    firebaseInfoLiveData?.value = "Login failure: ${it.exception?.localizedMessage}"
                }
            }
        }

    private fun addToDatabase(name: String, email: String){
        database?.child("users")?.child(firebaseAuth?.currentUser!!.uid)?.setValue(
            User(login = name, email = email, userID = firebaseAuth?.currentUser!!.uid)
        )
    }

    override suspend fun createEvent(event: Event): Unit = withContext(Dispatchers.IO){
        Log.d("CREATE", userLiveDatabase?.value.toString())
        val key = database?.push()?.key.toString()
        database?.child("users")?.child(firebaseAuth?.currentUser!!.uid)?.child("events")
            ?.child(key)
            ?.setValue(Event(key,userLiveDatabase?.value!!.login, event.date, event.name, event.description))
        sendInvites(key, Event(key,userLiveDatabase?.value!!.login, event.date, event.name, event.description))
        GlobalScope.launch(Dispatchers.Main) {
            firebaseInfoLiveData?.value = "Success"
        }
    }

    //грязно...
    override fun getUserFromFirebase() {
        database?.child("users")?.child(firebaseAuth?.currentUser!!.uid)
            ?.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayEvent = arrayListOf<Event>()
                for (snapshotOne in snapshot.child("events").children){
                    arrayEvent.add(
                        Event(creator = snapshotOne.child("creator").value.toString(),
                    name = snapshotOne.child("name").value.toString(),
                    description = snapshotOne.child("description").value.toString(),
                    date = snapshotOne.child("date").value.toString(),
                    key = snapshotOne.child("key").value.toString())
                    )
                }
                val arrayInvites = arrayListOf<Event>()
                for (snapshotOne in snapshot.child("invitations").children){
                    arrayInvites.add(
                        Event(creator = snapshotOne.child("creator").value.toString(),
                            name = snapshotOne.child("name").value.toString(),
                            description = snapshotOne.child("description").value.toString(),
                            date = snapshotOne.child("date").value.toString(),
                            key = snapshotOne.child("key").value.toString())
                    )
                }
                user = User(login = snapshot.child("login").value.toString(),
                email = snapshot.child("email").value.toString(),
                listOfEvents = arrayEvent,
                listOfInvitations = arrayInvites)
                updateUser()

            }
            override fun onCancelled(error: DatabaseError) {
                firebaseInfoLiveData?.value = "${error.message}}"
            }
        })
    }

    override suspend fun deleteEvent(event: Event): Unit = withContext(Dispatchers.IO){
        database?.child("users")?.child(firebaseAuth?.currentUser!!.uid)
            ?.child("events")?.child(event.key)?.removeValue()
    }

    //TODO сделать что-то с безопасностью...
    fun getAllUsersID(){
        Log.d("WORKS", "YES")
        val idList = arrayListOf<String>()
        database?.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshotOne in snapshot.child("users").children){
                    if (snapshotOne.key.toString() != firebaseAuth?.currentUser!!.uid)
                    idList.add(snapshotOne.key.toString())
                    Log.d("CHECKONE", idList.toString())
                }
                idListOne = idList
            }

            override fun onCancelled(error: DatabaseError) {
                //Log.d("USERID", error.message)
            }
        })
    }

    //TODO сделать теги
    fun sendInvites(key: String, event: Event){
        Log.d("CHECKONE", idListOne.toString())
        for (i in idListOne){
            database?.child("users")?.child(i)?.child("invitations")
                ?.child(key)
                ?.setValue(event)
        }
    }

    //TODO
    /*
    fun checkName(name: String){
        database?.child("users")?.orderByChild("names")?.equalTo(name)
    }
     */
    fun updateUser(){
        userLiveDatabase?.value = user
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
        return loggedOutLiveData
    }

    override fun logOut() {
        firebaseAuth!!.signOut()
        loggedOutLiveData!!.postValue(true)
    }

    override fun getUser(): LiveData<User>{
        return userLiveDatabase!!
    }

    override fun getFirebaseUser(): LiveData<FirebaseUser> {
        return firebaseUserLiveData!!
    }

    override fun getFirebaseInfo(): LiveData<String> {
        return firebaseInfoLiveData!!
    }

    override fun getEventByKey(key: String): Event {
        return user.listOfEvents.find {
            it.key == key
        } ?: throw Exception("Element with key $key is not existed")
    }
}