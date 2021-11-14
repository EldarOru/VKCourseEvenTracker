package com.example.eventracker.presentation.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.databinding.AddEventFragmentBinding
import com.example.eventracker.presentation.viewmodels.AddEventFragmentViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEventFragment: Fragment(), DatePickerDialog.OnDateSetListener {
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

        //TODO CHECK INPUT
        addEventFragmentBinding?.saveButton?.setOnClickListener {
            addEventFragmentViewModel.createNewEvent(
                addEventFragmentBinding?.eventNameEt?.text.toString(),
                addEventFragmentBinding?.eventDescriptionEt?.text.toString(),
                addEventFragmentBinding?.eventDateEt?.text.toString()
            )
            Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
        }

        addEventFragmentBinding?.eventDateEt?.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this.context as Context,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        val gregorianCalendar = GregorianCalendar(year, month, dayOfMonth)
        addEventFragmentBinding?.eventDateEt?.text = dateFormat.format(gregorianCalendar.time)
    }
}