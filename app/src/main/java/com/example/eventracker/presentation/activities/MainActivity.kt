package com.example.eventracker.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.eventracker.R
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.databinding.ActivityMainBinding
import com.example.eventracker.presentation.fragments.*

class MainActivity : AppCompatActivity(), OnFragmentsInteractionsListener {
    private var binding: ActivityMainBinding? = null

    //TODO исправить
    val generalRepositoryImpl = GeneralRepositoryImpl()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportFragmentManager.beginTransaction()
            .replace(binding?.mainContainer!!.id, LoginFragment())
            .replace(binding?.bottomContainer!!.id, AppInfoFragment())
            .commit()
    }

    override fun onLoginSuccess(
        mainEventFragment: MainEventFragment,
        bottomNavigationFragment: BottomNavigationFragment
    ) {
        supportFragmentManager.beginTransaction()
            .replace(binding?.mainContainer!!.id, mainEventFragment)
            .replace(binding?.bottomContainer!!.id, bottomNavigationFragment)
            .commit()
    }

    override fun onChangeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding?.mainContainer!!.id, fragment)
            .commit()
    }

    override fun onAddBackStack(name: String, fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .addToBackStack(name)
            .replace(R.id.main_container, fragment)
            .commit()
    }

    override fun onPopBackStack() {
        supportFragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        generalRepositoryImpl.logOut()
    }

    /*
    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }

     */
}