package com.kumar.nitchostelmanager.rooms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.databinding.FragmentAvailableRoomsBinding
import com.kumar.nitchostelmanager.models.Room
import com.kumar.nitchostelmanager.rooms.adapters.AvailableRoomsPagerAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AvailableRoomsFragment:Fragment(),CircleLoadingDialog {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private lateinit var binding:FragmentAvailableRoomsBinding
    val rooms = emptyArray<Room>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAvailableRoomsBinding.inflate(inflater,container,false)

        getRooms()
        binding.swipeRefreshLayoutInAvailableRoomsFragment.setOnRefreshListener {
            getRooms()
            binding.swipeRefreshLayoutInAvailableRoomsFragment.isRefreshing = false
        }
        return binding.root
    }

    private fun getRooms() {
        val getRoomsCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AvailableRoomsFragment)
        getRoomsCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
//            val rooms = emptyArray<Room>()
            if(!rooms.isNullOrEmpty()){
                sharedViewModel.availableRooms = rooms
                sharedViewModel.currentFloor = 0
                loadViewPager()
            }
            getRoomsCoroutineScope.cancel()
        }
    }

    private fun loadViewPager() {
        var floors = rooms.size/100
        for(i in 0..<floors){
            var floorString = "${i} floor"
            binding.tabLayoutInAvailableRoomsFragment.addTab(binding.tabLayoutInAvailableRoomsFragment.newTab().setText(floorString))
        }
//        binding.tabLayoutInAvailableRoomsFragment.addTab(binding.tabLayoutInAvailableRoomsFragment.newTab().setText("Mentors"))
        binding.viewPagerInAvailableRoomsFragment.adapter = AvailableRoomsPagerAdapter(childFragmentManager,lifecycle,floors)
        TabLayoutMediator(binding.tabLayoutInAvailableRoomsFragment,binding.viewPagerInAvailableRoomsFragment){tab,position->
            when(position){
                0-> {
                    tab.text = "0th floor"
                    sharedViewModel.currentFloor = 0
                }
                1-> {
                    tab.text = "1st floor"
                    sharedViewModel.currentFloor = 1
                }
                2-> {
                    tab.text = "2nd floor"
                    sharedViewModel.currentFloor = 2
                }
                3-> {
                    tab.text = "3rd floor"
                    sharedViewModel.currentFloor = 3
                }
                4-> {
                    tab.text = "4th floor"
                    sharedViewModel.currentFloor = 4
                }
            }
        }.attach()
    }
}