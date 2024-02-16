package com.kumar.nitchostelmanager.rooms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentAvailableRoomsBinding
import com.kumar.nitchostelmanager.hostels.access.HostelDataAccess
import com.kumar.nitchostelmanager.models.Room
import com.kumar.nitchostelmanager.rooms.access.ManageRoomAccess
import com.kumar.nitchostelmanager.rooms.adapters.AvailableRoomsPagerAdapter
import com.kumar.nitchostelmanager.rooms.adapters.FloorsAdapter
import com.kumar.nitchostelmanager.rooms.adapters.RoomsGridAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AvailableRoomsFragment : Fragment(), CircleLoadingDialog {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var binding: FragmentAvailableRoomsBinding

        var totalRooms = 0
    var rooms: Array<Room>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAvailableRoomsBinding.inflate(inflater, container, false)

        if (profileViewModel.userType == "Student") {

            if (sharedViewModel.viewingHostel != null) {
                binding.hostelNameTVInAvailableRoomsFragment.text =
                    sharedViewModel.viewingHostel!!.hostelID.toString()
//                totalRooms = sharedViewModel.viewingHostel!!.capacity
//                getAvailableRooms()
//            binding.swipeRefreshLayoutInAvailableRoomsFragment.setOnRefreshListener {
//                getRooms()
//                binding.swipeRefreshLayoutInAvailableRoomsFragment.isRefreshing = false
//            }
                getHostel(sharedViewModel.viewingHostel!!.hostelID)
            }else{
                findNavController().navigate(R.id.studentDashboardFragment)
            }
            } else {
            binding.availableRoomsTVInAvailableRoomsFragment.text = "All Rooms"
            binding.hostelNameTVInAvailableRoomsFragment.text =
                profileViewModel.currentWarden.hostelID.toString()
            getHostel(profileViewModel.currentWarden.hostelID.toString())
        }

        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
//                if(!sharedViewModel.fourthFloorRooms.isNullOrEmpty()){
//                    sharedViewModel.fourthFloorRooms.clear()
//                }
//                if(!sharedViewModel.secondFloorRooms.isNullOrEmpty()){
//                    sharedViewModel.secondFloorRooms.clear()
//                }
//                if(!sharedViewModel.thirdFloorRooms.isNullOrEmpty()){
//                    sharedViewModel.thirdFloorRooms.clear()
//                }
//                if(!sharedViewModel.groundFloorRooms.isNullOrEmpty()){
//                    sharedViewModel.groundFloorRooms.clear()
//                }
//                if(!sharedViewModel.firstFloorRooms.isNullOrEmpty()){
//                    sharedViewModel.firstFloorRooms.clear()
//                }
                if (profileViewModel.userType == "Student") {
                    findNavController().navigate(R.id.changeRoomFragment)
                } else {
                    findNavController().navigate(R.id.wardenDashboardFragment)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)
        return binding.root
    }


    private fun getHostel(hostelID: String){
        val hostelCoroutine = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AvailableRoomsFragment)
        hostelCoroutine.launch {
            val hostel = HostelDataAccess(requireContext(),this@AvailableRoomsFragment,profileViewModel.loginToken.toString()).getHostelDetails(hostelID)
            loadingDialog.cancel()
            hostelCoroutine.cancel()
            if(hostel != null){
                binding.hostelNameTVInAvailableRoomsFragment.text = hostel.hostelID.toString()
                totalRooms = hostel.capacity
                if(totalRooms > 0){
                    binding.floorsRecyclerViewInAvailableRoomsFragment.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                    binding.floorsRecyclerViewInAvailableRoomsFragment.adapter = FloorsAdapter(
                        totalRooms/100
                    ){floorSelected->
                        if(profileViewModel.userType == "Student"){
                            getAvailableRooms(hostelID,floorSelected)
                        }else{
                            getAllRooms(hostelID,floorSelected)
                        }
                    }
                    if(profileViewModel.userType == "Student"){
                        getAvailableRooms(hostelID,0)
                    }else{
                        getAllRooms(hostelID,0)
                    }
                }
            }
        }
    }
    private fun getAllRooms(hostelID: String, currentFloor: Int) {

        val roomsCoroutineScope = CoroutineScope(Dispatchers.Main)
        roomsCoroutineScope.launch {
            val rooms = ManageRoomAccess(
                requireContext(),
                profileViewModel.loginToken!!,
                this@AvailableRoomsFragment
            ).getAllRoomsFromTo(hostelID, (currentFloor * 100) + 1, (currentFloor + 1) * 100)
            roomsCoroutineScope.cancel()
            if (!rooms.isNullOrEmpty()) {
                binding.roomsRecyclerViewInAvailableRoomsFragment.layoutManager =
                    GridLayoutManager(context, 4)
                binding.roomsRecyclerViewInAvailableRoomsFragment.adapter = RoomsGridAdapter(
                    requireContext(),
                    profileViewModel.userType == "Student",
                    rooms
                ) { roomNumber ->
                    allocateRoom(hostelID, roomNumber)
                }
            }
        }
    }

    private fun getAvailableRooms(hostelID: String, currentFloor: Int) {

        val roomsCoroutineScope = CoroutineScope(Dispatchers.Main)
        roomsCoroutineScope.launch {
            val rooms = ManageRoomAccess(
                requireContext(),
                profileViewModel.loginToken!!,
                this@AvailableRoomsFragment
            ).getAvailableRoomsFromTo(
                hostelID,
                (currentFloor * 100) + 1,
                (currentFloor + 1) * 100
            )
            roomsCoroutineScope.cancel()
            if (!rooms.isNullOrEmpty()) {
                binding.roomsRecyclerViewInAvailableRoomsFragment.layoutManager =
                    GridLayoutManager(context, 4)
                binding.roomsRecyclerViewInAvailableRoomsFragment.adapter = RoomsGridAdapter(
                    requireContext(),
                    profileViewModel.userType == "Student",
                    rooms
                ) { roomNumber ->
                    allocateRoom(hostelID, roomNumber)
                }
            }
        }
    }


    private fun allocateRoom(hostelID: String, roomNumber: Int) {
        val allocateRoomCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(), this@AvailableRoomsFragment)
        allocateRoomCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val allocated = ManageRoomAccess(
                requireContext(),
                profileViewModel.loginToken.toString(),
                this@AvailableRoomsFragment
            ).allocateRoom(roomNumber, hostelID)
            loadingDialog.cancel()
            allocateRoomCoroutineScope.cancel()
            if (allocated) {
                Toast.makeText(context, "Allocated", Toast.LENGTH_SHORT).show()
                if (profileViewModel.userType == "Student") {
                    findNavController().navigate(R.id.studentDashboardFragment)
                } else findNavController().navigate(R.id.allStudentsFragment)
            }
        }
    }
//
//    private fun getAvailableRooms() {
//        val getRoomsCoroutineScope = CoroutineScope(Dispatchers.Main)
//        val loadingDialog = getLoadingDialog(requireContext(),this@AvailableRoomsFragment)
//        getRoomsCoroutineScope.launch {
//            loadingDialog.create()
//            loadingDialog.show()
//            rooms = ManageRoomAccess(
//                requireContext(),
//                profileViewModel.loginToken.toString(),
//                this@AvailableRoomsFragment
//            ).getAvailableRooms(sharedViewModel.viewingHostel!!.hostelID.toString())
//            loadingDialog.cancel()
//            getRoomsCoroutineScope.cancel()
//            if(!rooms.isNullOrEmpty()){
//                sharedViewModel.currentFloor = 0
//                sharedViewModel.rooms = rooms
//            }
//        }
//    }
//
//    private fun getAllRooms() {
//        val getRoomsCoroutineScope = CoroutineScope(Dispatchers.Main)
//        val loadingDialog = getLoadingDialog(requireContext(),this@AvailableRoomsFragment)
//        getRoomsCoroutineScope.launch {
//            loadingDialog.create()
//            loadingDialog.show()
//            rooms = ManageRoomAccess(
//                requireContext(),
//                profileViewModel.loginToken.toString(),
//                this@AvailableRoomsFragment
//            ).getAllRooms(profileViewModel.currentWarden.hostelID)
//            loadingDialog.cancel()
//            getRoomsCoroutineScope.cancel()
//            if(!rooms.isNullOrEmpty()){
//                sharedViewModel.currentFloor = 0
//                sharedViewModel.rooms = rooms
//                loadViewPager()
//            }
//        }
//    }

//    private fun loadViewPager() {
//        var floors = rooms!!.size/100
//        for(i in 0..<floors){
//            var floorString = "${i} floor"
//            binding.tabLayoutInAvailableRoomsFragment.addTab(binding.tabLayoutInAvailableRoomsFragment.newTab().setText(floorString))
//        }
////        binding.tabLayoutInAvailableRoomsFragment.addTab(binding.tabLayoutInAvailableRoomsFragment.newTab().setText("Mentors"))
//        binding.viewPagerInAvailableRoomsFragment.adapter = AvailableRoomsPagerAdapter(childFragmentManager,lifecycle,floors,sharedViewModel)
//        TabLayoutMediator(binding.tabLayoutInAvailableRoomsFragment,binding.viewPagerInAvailableRoomsFragment){tab,position->
////            when(position){
////                0-> {
////                    tab.text = "0th floor"
////                    sharedViewModel.currentFloor = 0
////                }
////                1-> {
////                    tab.text = "1st floor"
////                    sharedViewModel.currentFloor = 1
////                }
////                2-> {
////                    tab.text = "2nd floor"
////                    sharedViewModel.currentFloor = 2
////                }
////                3-> {
////                    tab.text = "3rd floor"
////                    sharedViewModel.currentFloor = 3
////                }
////                4-> {
////                    tab.text = "4th floor"
////                    sharedViewModel.currentFloor = 4
////                }
////            }
//            tab.text = "${position} Floor"
//        }.attach()
//    }
}