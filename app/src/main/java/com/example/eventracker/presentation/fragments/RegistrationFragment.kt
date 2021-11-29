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
import com.example.eventracker.databinding.RegistrationFragmentBinding
import com.example.eventracker.presentation.viewmodels.RegistrationFragmentViewModel

class RegistrationFragment: Fragment() {
    private lateinit var registrationFragmentViewModel: RegistrationFragmentViewModel
    private var registrationFragmentBinding: RegistrationFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registrationFragmentBinding = RegistrationFragmentBinding.inflate(inflater, container, false)
        return registrationFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrationFragmentViewModel = ViewModelProvider(this)[RegistrationFragmentViewModel::class.java]
        observeInput()
        addTextChangeListeners()
        
        registrationFragmentViewModel.shouldCloseScreen?.observe(viewLifecycleOwner){
            activity?.supportFragmentManager?.popBackStack()
        }

        registrationFragmentBinding?.backLoginActivity?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        registrationFragmentViewModel.getFirebaseInfoLiveData().observe(viewLifecycleOwner){
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }

        registrationFragmentBinding?.confirmRegisterButton?.setOnClickListener {
            val name: String = registrationFragmentBinding!!.nameEt.text.toString().trim()
            val email: String = registrationFragmentBinding!!.emailEt.text.toString().trim()
            val password: String = registrationFragmentBinding!!.passwordEt.text.toString().trim()
            val repeatPassword: String = registrationFragmentBinding!!.repeatPasswordEt.text.toString().trim()
            registrationFragmentViewModel.register(name, email, password, repeatPassword)
                //registrationFragmentViewModel.finishWork()
        }
    }


    private fun observeInput(){
        registrationFragmentViewModel.errorName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_name)
            } else {
                null
            }
            registrationFragmentBinding?.registrationNameTil?.error = message
        }

        registrationFragmentViewModel.errorEmail.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_email)
            } else {
                null
            }
            registrationFragmentBinding?.registrationEmailTil?.error = message
        }

        registrationFragmentViewModel.errorPassword.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_password)
            } else {
                null
            }
            registrationFragmentBinding?.registrationPasswordTil?.error = message
        }

        registrationFragmentViewModel.errorRepeatPassword.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_repeat_password)
            } else {
                null
            }
            registrationFragmentBinding?.registrationPasswordRepeatTil?.error = message
        }
    }

    private fun addTextChangeListeners(){
        registrationFragmentBinding!!.nameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                registrationFragmentViewModel.resetErrorName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        registrationFragmentBinding!!.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                registrationFragmentViewModel.resetErrorEmail()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        registrationFragmentBinding!!.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                registrationFragmentViewModel.resetErrorPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        registrationFragmentBinding!!.repeatPasswordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                registrationFragmentViewModel.resetErrorRepeatPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

}