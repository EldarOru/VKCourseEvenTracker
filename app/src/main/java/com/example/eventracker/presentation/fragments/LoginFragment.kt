package com.example.eventracker.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.R
import com.example.eventracker.databinding.LoginFragmentBinding
import com.example.eventracker.presentation.viewmodels.LoginFragmentViewModel
import com.example.eventracker.presentation.viewmodels.ViewModelFactory

class LoginFragment: Fragment() {
    private lateinit var loginFragmentViewModel: LoginFragmentViewModel
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
        loginFragmentViewModel = ViewModelProvider(this, ViewModelFactory())[LoginFragmentViewModel::class.java]
        observeInput()
        addTextChangeListeners()

        //TODO изменить взаимодействие активити и фрагментов
        loginFragmentViewModel.getUserLiveData().observe(viewLifecycleOwner){
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_container, MainEventFragment())
                ?.replace(R.id.bottom_container, BottomNavigationFragment())
                ?.commit()
        }

        loginFragmentViewModel.getFirebaseInfoLiveData().observe(viewLifecycleOwner){
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }

        loginFragmentBinding?.loginButton?.setOnClickListener {
            val email = loginFragmentBinding!!.loginEdittext.text.toString().trim()
            val password = loginFragmentBinding!!.passwordEdittext.text.toString().trim()
            loginFragmentViewModel.login(email, password)

        }

        loginFragmentBinding?.registrationButton?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.addToBackStack("login")
                ?.replace(R.id.main_container, RegistrationFragment())
                ?.commit()
        }
    }



    private fun observeInput(){
        loginFragmentViewModel.errorEmail.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_email)
            } else {
                null
            }
            loginFragmentBinding?.loginTil?.error = message
        }

        loginFragmentViewModel.errorPassword.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_password)
            } else {
                null
            }
            loginFragmentBinding?.passwordTil?.error = message
        }
    }

    private fun addTextChangeListeners(){
        loginFragmentBinding!!.loginEdittext.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginFragmentViewModel.resetErrorEmail()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        loginFragmentBinding!!.passwordEdittext.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginFragmentViewModel.resetErrorPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}