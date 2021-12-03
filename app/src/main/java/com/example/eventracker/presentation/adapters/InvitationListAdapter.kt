package com.example.eventracker.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventracker.databinding.EventBinding
import com.example.eventracker.databinding.EventInviteBinding
import com.example.eventracker.domain.models.Event

class InvitationListAdapter: RecyclerView.Adapter<InvitationListAdapter.EventInviteItemHolder>() {

    var onShopItemClickListener: ((Event) -> Unit)? = null

    //TODO CHANGE UPDATE
    var list = listOf<Event>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventInviteItemHolder {
        val eventView = EventInviteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventInviteItemHolder(eventView)
    }

    override fun onBindViewHolder(holder: EventInviteItemHolder, position: Int) {
        val event = list[position]
        holder.eventInviteBinding.eventInviteNameEt.text = event.name
        holder.eventInviteBinding.eventInviteDateEt.text = event.date
        holder.eventInviteBinding.eventInviteDescriptionEt.text = event.description
        /*
        holder.eventInviteBinding.root.setOnClickListener {
            onShopItemClickListener?.invoke(event)
        }
         */
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class EventInviteItemHolder(val eventInviteBinding: EventInviteBinding): RecyclerView.ViewHolder(eventInviteBinding.root)
}