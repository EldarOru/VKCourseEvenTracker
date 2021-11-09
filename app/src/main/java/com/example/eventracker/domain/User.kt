package com.example.eventracker.domain

data class User(val login: String,
                val email: String,
                val password: String,
                val listOfEvents: ArrayList<Event>,
                val listOfInvitations: ArrayList<String>)
