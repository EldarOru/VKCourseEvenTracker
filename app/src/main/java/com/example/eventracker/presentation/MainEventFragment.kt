package com.example.eventracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eventracker.databinding.LoginFragmentBinding
import com.example.eventracker.databinding.MainEventFragmentBinding

class MainEventFragment: Fragment() {
    private var mainEventFragmentBinding: MainEventFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainEventFragmentBinding = MainEventFragmentBinding.inflate(inflater, container, false)
        return mainEventFragmentBinding?.root
    }
}