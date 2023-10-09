package com.kumar.nitchostelmanager.rooms.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kumar.nitchostelmanager.rooms.fragments.Rooms101To200Fragment
import com.kumar.nitchostelmanager.rooms.fragments.Rooms1To100Fragment
import com.kumar.nitchostelmanager.rooms.fragments.Rooms201To300Fragment
import com.kumar.nitchostelmanager.rooms.fragments.Rooms301To400Fragment
import com.kumar.nitchostelmanager.rooms.fragments.Rooms401To500Fragment

class AvailableRoomsPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    var floorsCount:Int
) :FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return floorsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> Rooms1To100Fragment()
            1-> Rooms101To200Fragment()
            2->Rooms201To300Fragment()
            3-> Rooms301To400Fragment()
            4-> Rooms401To500Fragment()
            else->throw IllegalArgumentException("Invalid position $position")
        }
    }


}