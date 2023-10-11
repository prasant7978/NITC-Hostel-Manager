package com.kumar.nitchostelmanager.rooms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kumar.nitchostelmanager.databinding.Fragment101200RoomsBinding
import com.kumar.nitchostelmanager.models.Room
import com.kumar.nitchostelmanager.rooms.adapters.RoomsGridAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel

class Rooms101To200Fragment:Fragment() {
    private lateinit var binding:Fragment101200RoomsBinding
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment101200RoomsBinding.inflate(inflater,container,false)

//        val allRooms = sharedViewModel.availableRooms!!
//        var index = 0
//        var roomsToShow = arrayListOf<Room>()
//        for(room in allRooms){
//            if(room.roomNumber in 101..200){
//                roomsToShow.add(allRooms[index])
//            }else if(room.roomNumber>200) break
//        }
        binding.recyclerView101200InFragment.layoutManager = GridLayoutManager(context,4)
        binding.recyclerView101200InFragment.adapter = RoomsGridAdapter(
            requireContext(),
            profileViewModel,
            sharedViewModel.firstFloorRooms,
            sharedViewModel.currentFloor,
            this@Rooms101To200Fragment
        )
        return binding.root
    }
}