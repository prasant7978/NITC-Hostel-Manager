package com.kumar.nitchostelmanager.rooms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kumar.nitchostelmanager.databinding.Fragment301400RoomsBinding
import com.kumar.nitchostelmanager.rooms.adapters.RoomsGridAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel

class Rooms301To400Fragment:Fragment() {

    private lateinit var binding: Fragment301400RoomsBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment301400RoomsBinding.inflate(inflater,container,false)

        binding.recyclerView301400InFragment.layoutManager = GridLayoutManager(context,4)
        binding.recyclerView301400InFragment.adapter = RoomsGridAdapter(
            requireContext(),
            profileViewModel,
            sharedViewModel.thirdFloorRooms,
            sharedViewModel.currentFloor,
            this@Rooms301To400Fragment
        )
        return binding.root
    }
}