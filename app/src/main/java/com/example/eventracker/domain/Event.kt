package com.example.eventracker.domain

import java.util.*
import kotlin.collections.ArrayList

data class Event(
    val key: String = "",
    val creator: String = "",
    val date: String = "",
    val name: String = "",
    val description: String = "",
    val members: ArrayList<User> = arrayListOf()
) {
}