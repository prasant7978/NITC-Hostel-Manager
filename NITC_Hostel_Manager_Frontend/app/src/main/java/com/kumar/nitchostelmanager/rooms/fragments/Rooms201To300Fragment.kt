package com.kumar.nitchostelmanager.rooms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kumar.nitchostelmanager.databinding.Fragment201300RoomsBinding
import com.kumar.nitchostelmanager.rooms.adapters.RoomsGridAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel

class Rooms201To300Fragment:Fragment() {
    private lateinit var binding: Fragment201300RoomsBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment201300RoomsBinding.inflate(inflater,container,false)

        binding.gridView201300InFragment.adapter = RoomsGridAdapter(
            requireContext(),
            profileViewModel,
            sharedViewModel.availableRooms!!,
            sharedViewModel.currentFloor
        )
        return binding.root
    }
}