package com.kumar.nitchostelmanager.rooms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kumar.nitchostelmanager.databinding.Fragment401500RoomsBinding
import com.kumar.nitchostelmanager.rooms.adapters.RoomsGridAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel

class Rooms401To500Fragment:Fragment() {
    private lateinit var binding: Fragment401500RoomsBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment401500RoomsBinding.inflate(inflater,container,false)

        binding.recyclerView401500InFragment.layoutManager = GridLayoutManager(context,4)
        binding.recyclerView401500InFragment.adapter = RoomsGridAdapter(
            requireContext(),
            profileViewModel,
            sharedViewModel.fourthFloorRooms,
            sharedViewModel.currentFloor,
            this@Rooms401To500Fragment
        )
        return binding.root
    }
}