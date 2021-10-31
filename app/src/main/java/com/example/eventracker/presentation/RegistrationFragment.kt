package com.example.eventracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eventracker.databinding.RegistrationFragmentBinding

class RegistrationFragment: Fragment() {
    private var registrationFragmentBinding: RegistrationFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registrationFragmentBinding = RegistrationFragmentBinding.inflate(inflater, container, false)
        return registrationFragmentBinding?.root
    }
}