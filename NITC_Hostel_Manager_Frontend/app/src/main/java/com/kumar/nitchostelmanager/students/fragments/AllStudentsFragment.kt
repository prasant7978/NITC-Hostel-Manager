package com.kumar.nitchostelmanager.students.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentAllStudentsBinding
import com.kumar.nitchostelmanager.students.adapter.StudentsListPagerAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel

class AllStudentsFragment:Fragment() {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private lateinit var binding:FragmentAllStudentsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllStudentsBinding.inflate(inflater,container,false)

        loadViewPager()

        binding.addStudentButtonInAllStudents.setOnClickListener {
            findNavController().navigate(R.id.addStudentFragment)
        }
        binding.swipeRefreshLayoutInAllStudentsFragment.setOnRefreshListener {
            loadViewPager()
            binding.swipeRefreshLayoutInAllStudentsFragment.isRefreshing = false
        }

        return binding.root
    }

    private fun loadViewPager() {
        binding.tabLayoutInAllStudentsFragment.addTab(binding.tabLayoutInAllStudentsFragment.newTab().setText("Boys"))
        binding.tabLayoutInAllStudentsFragment.addTab(binding.tabLayoutInAllStudentsFragment.newTab().setText("Girls"))
        binding.viewPagerInAllStudentsFragment.adapter = StudentsListPagerAdapter(childFragmentManager,lifecycle)
        TabLayoutMediator(binding.tabLayoutInAllStudentsFragment,binding.viewPagerInAllStudentsFragment){tab,position->
            when(position){
                0-> tab.text = "Boys"
                1-> tab.text = "Girls"
            }
        }.attach()
    }

}