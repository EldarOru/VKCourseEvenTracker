package com.example.eventracker.presentation.viewmodels

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.eventracker.data.RetrofitClient
import com.example.eventracker.data.RetrofitServices
import com.example.eventracker.domain.LoginBody
import com.example.eventracker.domain.User
import com.example.eventracker.presentation.fragments.RegistrationFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import kotlin.concurrent.timerTask

class RegistrationFragmentViewModel: ViewModel() {
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]+"
/*
    fun registration(login: String, email: String, password: String, repeatPassword: String): Boolean{
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        if(checkInputData(email, login, password, repeatPassword)){
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        mAuth.currentUser!!.sendEmailVerification()
                        fireBaseUserID = mAuth.currentUser!!.uid
                        database = FirebaseDatabase.getInstance().getReference()
                        val user = User(email, name, checkGender(radioButton))
                        database.child("users").child(fireBaseUserID).setValue(user)
                        Toast.makeText(this, "You have successfully registered, check your email for verification",
                            Toast.LENGTH_SHORT).show()
                        Timer("Left", false).schedule(timerTask { startActivity(loginIntent)}, Toast.LENGTH_SHORT.toLong())
                        //работает ли вообще эта функция..?
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Error Message: " + task.exception!!.message,
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun checkInputData(email: String, name: String, password: String, repeatPassword: String):Boolean{
        return if (!email.matches(emailPattern.toRegex())){
            false
        }else if(name.isEmpty()) {
            false
        }else if(password != repeatPassword || password.isEmpty()){
            false
        }else true
    }
    /*
    fun registration (login: String, email: String, password: String): Boolean{
        var flag = false
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitClient.retrofitServices.reg(User(login,email, password))
            } catch (ex: IOException){
                return@launch
            }
            if (response.code() == RetrofitServices.CORRECT_AUTO) {
                flag = true
                return@launch
            }
        }
        return flag
    }
    //add check user's input

     */

 */
}