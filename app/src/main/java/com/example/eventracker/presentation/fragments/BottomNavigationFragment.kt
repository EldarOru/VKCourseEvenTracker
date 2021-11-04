package com.example.eventracker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eventracker.databinding.BottomNavigationFragmentBinding

class BottomNavigationFragment: Fragment() {
    private var bottomNavigationFragmentBinding: BottomNavigationFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationFragmentBinding =  BottomNavigationFragmentBinding.inflate(inflater, container, false)
        return bottomNavigationFragmentBinding?.root
    }
}