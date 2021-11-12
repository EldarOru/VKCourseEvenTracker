package com.example.eventracker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.databinding.AddEventFragmentBinding
import com.example.eventracker.presentation.viewmodels.AddEventFragmentViewModel

class AddEventFragment: Fragment() {
    private var addEventFragmentBinding: AddEventFragmentBinding? = null
    private lateinit var addEventFragmentViewModel: AddEventFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addEventFragmentBinding = AddEventFragmentBinding.inflate(inflater, container, false)
        return addEventFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addEventFragmentViewModel = ViewModelProvider(this)[AddEventFragmentViewModel::class.java]

        addEventFragmentBinding?.saveButton?.setOnClickListener {
            addEventFragmentViewModel.createNewEvent(
                addEventFragmentBinding?.eventNameEt?.text.toString(),
                addEventFragmentBinding?.eventDescriptionEt?.text.toString()
            )
            Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
}