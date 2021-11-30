package com.example.eventracker.presentation.fragments

import androidx.fragment.app.Fragment

interface OnFragmentsInteractionsListener {

    fun onLoginSuccess(mainEventFragment: MainEventFragment,
                       bottomNavigationFragment: BottomNavigationFragment)

    fun onChangeFragment(fragment: Fragment)

    fun onAddBackStack(name: String, fragment: Fragment)

    fun onPopBackStack()
}