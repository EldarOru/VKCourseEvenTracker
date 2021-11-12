package com.example.eventracker.domain

import java.util.*
import kotlin.collections.ArrayList

data class Event(
    val creator: String = "",
    val data: Date? = null,
    val name: String = "",
    val description: String = "",
    val members: ArrayList<User> = arrayListOf()
) {
}