package com.example.eventracker.presentation.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventracker.R
import com.example.eventracker.databinding.AddEventFragmentBinding
import com.example.eventracker.presentation.viewmodels.AddEventFragmentViewModel
import com.example.eventracker.presentation.viewmodels.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class AddEventFragment: Fragment(), DatePickerDialog.OnDateSetListener {

    private var addEventFragmentBinding: AddEventFragmentBinding? = null
    private lateinit var addEventFragmentViewModel: AddEventFragmentViewModel

    private lateinit var onFragmentsInteractionsListener: OnFragmentsInteractionsListener

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
        addEventFragmentBinding = AddEventFragmentBinding.inflate(inflater, container, false)
        return addEventFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEventFragmentViewModel = ViewModelProvider(this, ViewModelFactory())[AddEventFragmentViewModel::class.java]
        observeInput()
        addTextChangeListeners()

        addEventFragmentBinding?.saveButton?.setOnClickListener {
            val eventName: String = addEventFragmentBinding?.eventNameEt?.text.toString()
            val eventDescription: String = addEventFragmentBinding?.eventDescriptionEt?.text.toString()
            val date: String = addEventFragmentViewModel.checkDate(addEventFragmentBinding?.eventDateEt?.text.toString())
            val time: String = addEventFragmentViewModel.checkTime(addEventFragmentBinding?.eventTimeEt?.text.toString())
            if (addEventFragmentViewModel.validateInput(eventName, eventDescription)) {
                onFragmentsInteractionsListener.onAddBackStack("mapFragment",
                    MapFragment.newInstanceMapFragment(
                        date = date,
                        name = eventName,
                        description = eventDescription,
                        time = time
                    )
                )
            }
        }

        addEventFragmentBinding?.eventDateEt?.setOnClickListener {
            showDatePickerDialog()
        }

        addEventFragmentViewModel.getFirebaseInfoLiveData().observe(viewLifecycleOwner){
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }

        addEventFragmentBinding?.eventTimeEt?.setOnClickListener {
            showTimePickerDialog()
        }
    }

    //TODO NORMAL DATE, NOT STRING
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        val gregorianCalendar = GregorianCalendar(year, month, dayOfMonth)
        addEventFragmentBinding?.eventDateEt?.text = dateFormat.format(gregorianCalendar.time)
    }


    private fun showTimePickerDialog(){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)

            addEventFragmentBinding?.eventTimeEt?.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(context,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
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

    private fun observeInput() {
        addEventFragmentViewModel.errorName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_name)
            } else {
                null
            }
            addEventFragmentBinding?.eventNameTil?.error = message
        }

        addEventFragmentViewModel.errorDescription.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_description)
            } else {
                null
            }
            addEventFragmentBinding?.eventDescriptionTil?.error = message
        }
    }

    private fun addTextChangeListeners() {
        addEventFragmentBinding!!.eventNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addEventFragmentViewModel.resetErrorName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        addEventFragmentBinding!!.eventDescriptionEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addEventFragmentViewModel.resetErrorDescription()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}