package com.example.eventracker.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventracker.databinding.EventBinding
import com.example.eventracker.domain.Event

class EventListAdapter: RecyclerView.Adapter<EventListAdapter.EventItemViewHolder>() {

    //TODO CHANGE UPDATE
    var list = listOf<Event>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        val eventView = EventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventItemViewHolder(eventView)
    }

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        val event = list[position]
        holder.eventBinding.eventNameEt.text = event.name
        holder.eventBinding.eventDateEt.text = event.date
        holder.eventBinding.eventDescriptionEt.text = event.description
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class EventItemViewHolder(val eventBinding: EventBinding): RecyclerView.ViewHolder(eventBinding.root)
}