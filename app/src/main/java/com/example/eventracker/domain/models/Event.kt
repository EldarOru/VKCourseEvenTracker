package com.example.eventracker.domain.models

import kotlin.collections.ArrayList

data class Event(
    val key: String = UNDEFINED_KEY,
    val creator: String = "",
    val date: String = "",
    val name: String = "",
    val description: String = "",
    val members: ArrayList<User> = arrayListOf()
){
    companion object{
        const val UNDEFINED_KEY = "-1"
    }
}
