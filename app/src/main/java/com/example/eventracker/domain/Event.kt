package com.example.eventracker.domain

import java.util.*
import kotlin.collections.ArrayList

data class Event(
    val data: Date,
    val name: String,
    val members: ArrayList<User>
) {
}