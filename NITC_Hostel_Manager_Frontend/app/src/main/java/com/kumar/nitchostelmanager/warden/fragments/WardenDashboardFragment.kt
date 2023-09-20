package com.kumar.nitchostelmanager.warden.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kumar.nitchostelmanager.databinding.FragmentWardenDashboardBinding

class WardenDashboardFragment:Fragment() {

    private lateinit var binding:FragmentWardenDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWardenDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

}