package com.example.eventracker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eventracker.data.GeneralRepository
import com.example.eventracker.databinding.LoginFragmentBinding
import com.example.eventracker.databinding.MainEventFragmentBinding

class MainEventFragment: Fragment() {
    private var mainEventFragmentBinding: MainEventFragmentBinding? = null
    private lateinit var generalRepository: GeneralRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainEventFragmentBinding = MainEventFragmentBinding.inflate(inflater, container, false)
        return mainEventFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generalRepository = GeneralRepository(this.context)
        Toast.makeText(this.context, generalRepository.getUserLiveData()?.value?.email.toString(), Toast.LENGTH_SHORT).show()
    }
}