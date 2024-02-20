package com.kumar.nitchostelmanager.hostels.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentViewHostelBinding
import com.kumar.nitchostelmanager.hostels.access.ManageHostelsAccess
import com.kumar.nitchostelmanager.hostels.adapters.HostelListAdapter
import com.kumar.nitchostelmanager.hostels.adapters.HostelListStudentAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import com.kumar.nitchostelmanager.viewModel.ViewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ViewHostelFragment : Fragment() {
    private lateinit var binding: FragmentViewHostelBinding
    private lateinit var hostelListAdapter: HostelListStudentAdapter
    val profileViewModel: ProfileViewModel by activityViewModels()
    private val viewsViewModel: ViewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_view_hostel, container, false)

        binding.lifecycleOwner = this
        binding.mainViewModel = viewsViewModel
        viewsViewModel.updateLoadingState(true)

        getAllHostels()

        return binding.root
    }

    private fun getAllHostels(){
        val getHostelsCoroutineScope = CoroutineScope(Dispatchers.Main)

        getHostelsCoroutineScope.launch {
            viewsViewModel.updateLoadingState(true)

            val hostelList = ManageHostelsAccess(
                requireContext(),
                profileViewModel.loginToken.toString(),
                this@ViewHostelFragment
            ).getHostels()

            getHostelsCoroutineScope.cancel()
            viewsViewModel.updateLoadingState(false)

            if(!hostelList.isNullOrEmpty()){
                binding.recyclerViewInViewHostelFragment.layoutManager = LinearLayoutManager(context)
                hostelListAdapter = HostelListStudentAdapter(hostelList)
                binding.recyclerViewInViewHostelFragment.adapter = hostelListAdapter
            }
            else{
                binding.recyclerViewInViewHostelFragment.visibility = View.GONE
                Toast.makeText(context, "No hostels are available", Toast.LENGTH_SHORT).show()
            }
        }
    }
}