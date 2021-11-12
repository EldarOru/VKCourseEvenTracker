package com.example.eventracker.presentation.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.R
import com.example.eventracker.data.GeneralRepository
import com.example.eventracker.databinding.RegistrationFragmentBinding
import com.example.eventracker.presentation.viewmodels.LoginFragmentViewModel
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
        /*
        registrationFragmentViewModel = ViewModelProvider(this)[RegistrationFragmentViewModel::class.java]
        registrationFragmentBinding?.confirmRegisterButton?.setOnClickListener {
            if (registrationFragmentViewModel.registration(
                    registrationFragmentBinding!!.nameEt.text.toString(),
                    registrationFragmentBinding!!.emailEt.text.toString(),
                    registrationFragmentBinding!!.passwordEt.text.toString())){
                Toast.makeText(activity,"Success", Toast.LENGTH_LONG).show()
                activity?.supportFragmentManager?.popBackStack()
            }else{
                Toast.makeText(activity,"Something is wrong", Toast.LENGTH_LONG).show()
            }
        }

         */
        registrationFragmentViewModel.shouldCloseScreen.observe(viewLifecycleOwner){
            activity?.supportFragmentManager?.popBackStack()
        }

        registrationFragmentBinding?.backLoginActivity?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        registrationFragmentBinding?.confirmRegisterButton?.setOnClickListener {
            val name: String = registrationFragmentBinding!!.nameEt.text.toString()
            val email: String = registrationFragmentBinding!!.emailEt.text.toString()
            val password: String = registrationFragmentBinding!!.passwordEt.text.toString()
            if (registrationFragmentViewModel.checkInput(name, email, password)) {
                registrationFragmentBinding?.progressBarRegister!!.visibility = View.GONE
                registrationFragmentViewModel.register(name, email, password)
                //registrationFragmentViewModel.finishWork()
            }else Toast.makeText(this.context, "Incorrect input", Toast.LENGTH_SHORT).show()
        }
    }


}