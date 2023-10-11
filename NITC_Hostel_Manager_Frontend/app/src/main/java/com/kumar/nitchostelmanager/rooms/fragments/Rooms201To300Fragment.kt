package com.kumar.nitchostelmanager.rooms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kumar.nitchostelmanager.databinding.Fragment201300RoomsBinding
import com.kumar.nitchostelmanager.models.Room
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

//        val allRooms = sharedViewModel.availableRooms!!
//        var index = 0
//        var roomsToShow = arrayListOf<Room>()
//        for(room in allRooms){
//            if(room.roomNumber in 201..300){
//                roomsToShow.add(allRooms[index])
//            }else if(room.roomNumber>200) break
//        }
        binding.recyclerView201300InFragment.layoutManager = GridLayoutManager(context,4)
        binding.recyclerView201300InFragment.adapter = RoomsGridAdapter(
            requireContext(),
            profileViewModel,
            sharedViewModel.secondFloorRooms,
            sharedViewModel.currentFloor,
            this@Rooms201To300Fragment
        )
        return binding.root
    }
}