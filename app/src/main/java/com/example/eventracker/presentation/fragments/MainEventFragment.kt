package com.example.eventracker.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventracker.R
import com.example.eventracker.data.GeneralRepositoryImpl
import com.example.eventracker.databinding.MainEventFragmentBinding
import com.example.eventracker.presentation.adapters.EventListAdapter
import com.example.eventracker.presentation.viewmodels.MainFragmentViewModel
import com.example.eventracker.presentation.viewmodels.ViewModelFactory
import java.lang.RuntimeException

class MainEventFragment: Fragment() {
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private lateinit var onFragmentsInteractionsListener: OnFragmentsInteractionsListener
    private var mainEventFragmentBinding: MainEventFragmentBinding? = null

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
        mainEventFragmentBinding = MainEventFragmentBinding.inflate(inflater, container, false)
        return mainEventFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentViewModel = ViewModelProvider(this, ViewModelFactory())[MainFragmentViewModel::class.java]
        setRecyclerView()
        setupClickListener()

        mainFragmentViewModel.getUserLiveDatabase().observe(viewLifecycleOwner){
            eventListAdapter.list = it.listOfEvents
        }

        mainEventFragmentBinding?.buttonAddEvent?.setOnClickListener {
            onFragmentsInteractionsListener.onAddBackStack("addEvent", AddEventFragment())
        }
    }

    private fun setupClickListener() {
        eventListAdapter.onShopItemClickListener = {
            onFragmentsInteractionsListener.onAddBackStack(
                "event",
                DetailedEventFragment.newInstanceDetailedEventFragment(GeneralRepositoryImpl.GET_FROM_EVENT_LIST, it.key))
        }
    }

    private fun setRecyclerView(){
        val recyclerView = mainEventFragmentBinding?.eventList
        recyclerView?.layoutManager = LinearLayoutManager(context)
        eventListAdapter = EventListAdapter()
        recyclerView?.adapter = eventListAdapter
        setupSwipeListener(recyclerView as RecyclerView)

    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val myCallBack = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = eventListAdapter.list[viewHolder.adapterPosition]
                mainFragmentViewModel.deleteEvent(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(myCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}