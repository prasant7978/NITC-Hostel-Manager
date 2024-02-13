package com.kumar.nitchostelmanager.rooms.adapters

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kumar.nitchostelmanager.rooms.fragments.FloorFragment
import com.kumar.nitchostelmanager.viewModel.SharedViewModel

class AvailableRoomsPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    var floorsCount:Int,
    var sharedViewModel: SharedViewModel
) :FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return floorsCount
    }

    override fun createFragment(position: Int): Fragment {
        sharedViewModel.currentFloor = position
        Log.d("loadPage","Set current floor value = $position")
        return FloorFragment()
    }


}