package com.kumar.nitchostelmanager.students.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.databinding.FragmentOccupantsBinding
import com.kumar.nitchostelmanager.hostels.access.HostelDataAccess
import com.kumar.nitchostelmanager.students.adapter.OccupantsAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OccupantsFragment:Fragment(),CircleLoadingDialog {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()

    private lateinit var binding:FragmentOccupantsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOccupantsBinding.inflate(inflater,container,false)

        getOccupants()

        return binding.root
    }

    private fun getOccupants() {
        val getOccupantsCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@OccupantsFragment)
        loadingDialog.create()
        getOccupantsCoroutineScope.launch {
            loadingDialog.show()
            val occupants = HostelDataAccess(
                requireContext(),
                this@OccupantsFragment,
                profileViewModel.loginToken.toString()
            ).getHostelOccupants(profileViewModel.currentWarden.hostelID)
            loadingDialog.cancel()
            getOccupantsCoroutineScope.cancel()
            if(occupants != null){
                binding.recyclerViewInOccupantsFragment.layoutManager = LinearLayoutManager(context)
                binding.recyclerViewInOccupantsFragment.adapter = OccupantsAdapter(
                    occupants,
                    sharedViewModel,
                    this@OccupantsFragment
                )
            }
        }
    }

}