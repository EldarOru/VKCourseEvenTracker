package com.example.eventracker.domain

data class User(val login: String = "",
                val email: String = "",
                val listOfEvents: ArrayList<Event> = arrayListOf(),
                val listOfInvitations: ArrayList<String> = arrayListOf())
