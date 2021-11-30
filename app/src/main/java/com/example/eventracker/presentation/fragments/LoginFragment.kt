package com.example.eventracker.presentation.fragments

import android.content.Context
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
import java.lang.RuntimeException

class LoginFragment: Fragment() {
    private lateinit var loginFragmentViewModel: LoginFragmentViewModel
    private lateinit var onFragmentsInteractionsListener: OnFragmentsInteractionsListener
    private var loginFragmentBinding: LoginFragmentBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentsInteractionsListener){
            onFragmentsInteractionsListener = context
        }else{
            throw RuntimeException("Activity must implement OnFragmentsInteractionsListener")
        }
    }

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

        loginFragmentViewModel.getUserLiveData().observe(viewLifecycleOwner){
            onFragmentsInteractionsListener.onLoginSuccess(
                mainEventFragment = MainEventFragment(),
                bottomNavigationFragment = BottomNavigationFragment()
            )
        }

        //TODO перенести тост в активити
        loginFragmentViewModel.getFirebaseInfoLiveData().observe(viewLifecycleOwner){
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }

        loginFragmentBinding?.loginButton?.setOnClickListener {
            val email = loginFragmentBinding!!.loginEdittext.text.toString().trim()
            val password = loginFragmentBinding!!.passwordEdittext.text.toString().trim()
            loginFragmentViewModel.login(email, password)

        }

        loginFragmentBinding?.registrationButton?.setOnClickListener {
            onFragmentsInteractionsListener.onAddBackStack("login", RegistrationFragment())
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