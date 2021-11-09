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
import com.example.eventracker.databinding.LoginFragmentBinding
import com.example.eventracker.presentation.viewmodels.LoginFragmentViewModel

class LoginFragment: Fragment() {
    private lateinit var loginFragmentViewModel: LoginFragmentViewModel
    private var loginFragmentBinding: LoginFragmentBinding? = null
    private lateinit var generalRepository: GeneralRepository
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
        /*
        loginFragmentViewModel = ViewModelProvider(this)[LoginFragmentViewModel::class.java]
        loginFragmentBinding?.loginButton?.setOnClickListener {
            if (loginFragmentViewModel.authorization(
                    loginFragmentBinding!!.loginEdittext.text.toString(),
                    loginFragmentBinding!!.passwordEdittext.text.toString())){
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_container, MainEventFragment())
                    ?.replace(R.id.bottom_container, BottomNavigationFragment())
                    ?.commit()
            }else{
                Toast.makeText(activity,"Something is wrong",Toast.LENGTH_LONG).show()
            }
        }

         */
        //TODO
        generalRepository = GeneralRepository(this.requireActivity().application)
        generalRepository.getUserLiveData()?.observe(viewLifecycleOwner){
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_container, MainEventFragment())
                ?.replace(R.id.bottom_container, BottomNavigationFragment())
                ?.commit()
        }

        loginFragmentBinding?.loginButton?.setOnClickListener {
            generalRepository.login(loginFragmentBinding!!.loginEdittext.text.toString(),
                loginFragmentBinding!!.passwordEdittext.text.toString())
        }

        loginFragmentBinding?.registrationButton?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.addToBackStack("login")
                ?.replace(R.id.main_container, RegistrationFragment())
                ?.commit()
        }
    }
    //change everything
}