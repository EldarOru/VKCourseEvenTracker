package com.example.eventracker.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eventracker.databinding.ActivityMainBinding
import com.example.eventracker.presentation.fragments.AppInfoFragment
import com.example.eventracker.presentation.fragments.LoginFragment

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

    /*
    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }

     */
}