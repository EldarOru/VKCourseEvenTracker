package com.example.eventracker.data

import com.example.eventracker.domain.LoginBody
import com.example.eventracker.domain.User
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServices {
    @POST("/user/auth")
    fun auth(@Body loginBody: LoginBody): Response

    @POST("/user")
    fun reg(@Body user: User): Response

    //todo
    companion object {
        const val URL = "TODO"
        const val CORRECT_AUTO = 200
    }
}