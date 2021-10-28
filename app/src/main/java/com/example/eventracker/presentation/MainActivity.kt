package com.example.eventracker.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.eventracker.R
import com.example.eventracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportFragmentManager.beginTransaction()
            .replace(binding?.mainContainer!!.id, LoginFragment())
            .replace(binding?.bottomContainer!!.id, AppInfoFragment())
            .commit()

    }
}