package com.kumar.nitchostelmanager.rooms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kumar.nitchostelmanager.databinding.Fragment1100RoomsBinding
import com.kumar.nitchostelmanager.models.Room
import com.kumar.nitchostelmanager.rooms.adapters.RoomsGridAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel

class Rooms1To100Fragment:Fragment() {
    private lateinit var binding:Fragment1100RoomsBinding
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment1100RoomsBinding.inflate(inflater,container,false)


//        var index = 0
//        var roomsToShow = arrayListOf<Room>()
//        for(room in allRooms){
//            if(room.roomNumber<=100){
//                roomsToShow.add(allRooms[index])
//            }else break
//        }
        binding.recyclerView0100InFragment.layoutManager = GridLayoutManager(context,4)
        binding.recyclerView0100InFragment.adapter = RoomsGridAdapter(
            requireContext(),
            profileViewModel,
            sharedViewModel.groundFloorRooms,
            sharedViewModel.currentFloor,
            this@Rooms1To100Fragment
        )

        return binding.root
    }
}