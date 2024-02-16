package com.kumar.nitchostelmanager.rooms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentFloorBinding
import com.kumar.nitchostelmanager.rooms.access.ManageRoomAccess
import com.kumar.nitchostelmanager.rooms.adapters.RoomsGridAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FloorFragment:Fragment(),CircleLoadingDialog {
    private lateinit var binding:FragmentFloorBinding
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFloorBinding.inflate(inflater,container,false)

//        val allRooms = sharedViewModel.availableRooms!!
//        var index = 0
//        var roomsToShow = arrayListOf<Room>()
//        for(room in allRooms){
//            if(room.roomNumber in 101..200){
//                roomsToShow.add(allRooms[index])
//            }else if(room.roomNumber>200) break
//        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(isAdded){
            if(profileViewModel.userType == "Student") getAvailableRooms(sharedViewModel.viewingHostel!!.hostelID,sharedViewModel.currentFloor)
            else getAllRooms(profileViewModel.currentWarden.hostelID,sharedViewModel.currentFloor)
        }
    }

    private fun getAllRooms(hostelID:String,currentFloor: Int) {

        if(!isAdded){
            getAllRooms(hostelID, currentFloor)
            return
        }
        val roomsCoroutineScope = CoroutineScope(Dispatchers.Main)
        roomsCoroutineScope.launch {
            if(!isAdded) {
                roomsCoroutineScope.cancel()
                getAllRooms(hostelID,currentFloor)
                return@launch
            }
            val rooms = ManageRoomAccess(
                requireContext(),
                profileViewModel.loginToken!!,
                this@FloorFragment
            ).getAllRoomsFromTo(hostelID,(currentFloor*100)+1,(currentFloor+1)*100)
            roomsCoroutineScope.cancel()
            if(!rooms.isNullOrEmpty()){
                if(!isAdded) {
                    roomsCoroutineScope.cancel()
                    getAllRooms(hostelID,currentFloor)
                    return@launch
                }
                binding.recyclerViewFloorFragment.layoutManager = GridLayoutManager(context,4)
                binding.recyclerViewFloorFragment.adapter = RoomsGridAdapter(
                    requireContext(),
                    profileViewModel.userType == "Student",
                    rooms
                ){roomNumber->
                    allocateRoom(hostelID,roomNumber)
                }
            }
        }

    }
    private fun getAvailableRooms(hostelID:String,currentFloor: Int) {

        if(!isAdded) {
            getAvailableRooms(hostelID,currentFloor)
            return
        }
        val roomsCoroutineScope = CoroutineScope(Dispatchers.Main)
        roomsCoroutineScope.launch {
            if(!isAdded) {
                roomsCoroutineScope.cancel()
                getAvailableRooms(hostelID,currentFloor)
                return@launch
            }
            val rooms = ManageRoomAccess(
                requireContext(),
                profileViewModel.loginToken!!,
                this@FloorFragment
            ).getAvailableRoomsFromTo(hostelID,(currentFloor*100)+1,(currentFloor+1)*100)
            roomsCoroutineScope.cancel()
            if(!rooms.isNullOrEmpty()){
                if(!isAdded) {
                    roomsCoroutineScope.cancel()
                    getAvailableRooms(hostelID,currentFloor)
                    return@launch
                }
                binding.recyclerViewFloorFragment.layoutManager = GridLayoutManager(context,4)
                binding.recyclerViewFloorFragment.adapter = RoomsGridAdapter(
                    requireContext(),
                    profileViewModel.userType == "Student",
                    rooms
                ){roomNumber->
                    allocateRoom(hostelID,roomNumber)
                }
            }
        }

    }



    private fun allocateRoom(hostelID:String,roomNumber: Int){
        val allocateRoomCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(), this@FloorFragment )
        allocateRoomCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val allocated = ManageRoomAccess(
                requireContext(),
                profileViewModel.loginToken.toString(),
                this@FloorFragment
            ).allocateRoom(roomNumber,hostelID)
            loadingDialog.cancel()
            allocateRoomCoroutineScope.cancel()
            if(allocated){
                Toast.makeText(context,"Allocated", Toast.LENGTH_SHORT).show()
                if(profileViewModel.userType == "Student"){
                    findNavController().navigate(R.id.studentDashboardFragment)
                }
                else findNavController().navigate(R.id.allStudentsFragment)
            }
        }
    }
}