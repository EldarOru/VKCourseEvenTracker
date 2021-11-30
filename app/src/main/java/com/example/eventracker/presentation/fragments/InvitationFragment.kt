package com.example.eventracker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eventracker.databinding.InvitationFragmentBinding

class InvitationFragment: Fragment() {
    private var invitationFragmentBinding: InvitationFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        invitationFragmentBinding =  InvitationFragmentBinding.inflate(inflater, container, false)
        return invitationFragmentBinding?.root
    }
}