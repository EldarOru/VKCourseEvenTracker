package com.example.eventracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eventracker.R
import com.example.eventracker.databinding.LoginFragmentBinding

class LoginFragment: Fragment() {
    private var loginFragmentBinding: LoginFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginFragmentBinding = LoginFragmentBinding.inflate(inflater, container, false)
        return loginFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginFragmentBinding?.loginButton?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_container, MainEventFragment())
                ?.replace(R.id.bottom_container, BottomNavigationFragment())
                ?.commit()
        }
        loginFragmentBinding?.registrationButton?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.main_container, RegistrationFragment())
                ?.commit()
        }
    }
    //change everything
}