package com.kumar.nitchostelmanager.hostels.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentAllHostelsBinding
import com.kumar.nitchostelmanager.hostels.access.ManageHostelsAccess
import com.kumar.nitchostelmanager.hostels.adapters.HostelListAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AllHostelsFragment:Fragment() {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private lateinit var binding:FragmentAllHostelsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllHostelsBinding.inflate(inflater,container,false)

        getHostels()

        binding.addHostelButtonInAllHostelsFragment.setOnClickListener{
            sharedViewModel.updatingHostelID = null
            findNavController().navigate(R.id.addHostelFragment)
        }

        return binding.root
    }

    private fun deleteHostel(hostelID:String) {
        val deleteCoroutineScope = CoroutineScope(Dispatchers.Main)
        deleteCoroutineScope.launch {
            val deleted = ManageHostelsAccess(
                requireContext(),
                profileViewModel.loginToken.toString(),
                this@AllHostelsFragment
            ).deleteHostel(hostelID)
            deleteCoroutineScope.cancel()
            if(deleted){
                Toast.makeText(context,"Hostel deleted",Toast.LENGTH_SHORT).show()
                getHostels()
            }
        }
    }
    private fun getHostels() {
        var getwardenCoroutineScope = CoroutineScope(Dispatchers.Main)
        getwardenCoroutineScope.launch {
            var hostels = ManageHostelsAccess(
                requireContext(),
                profileViewModel.loginToken.toString(),
                this@AllHostelsFragment
            ).getHostels()
            if(!hostels.isNullOrEmpty()){
                binding.hostelsRecyclerViewInAllHostelsFragment.visibility = View.VISIBLE
                binding.noHostelsTVInAllHostelsFragment.visibility = View.INVISIBLE
                binding.hostelsRecyclerViewInAllHostelsFragment.layoutManager = LinearLayoutManager(context)
                binding.hostelsRecyclerViewInAllHostelsFragment.adapter = HostelListAdapter(
                    requireContext(),
                    profileViewModel.loginToken.toString(),
                    this@AllHostelsFragment,
                    sharedViewModel,
                    hostels,
                    {hostelID->
                        sharedViewModel.updatingHostelID = hostelID
                        findNavController().navigate(R.id.addHostelFragment)
                    },
                    {hostelID->
                        deleteHostel(hostelID)
                    }
                )
            }else{
                binding.hostelsRecyclerViewInAllHostelsFragment.visibility = View.GONE
                binding.noHostelsTVInAllHostelsFragment.visibility = View.VISIBLE
                Toast.makeText(context,"No hostels till now", Toast.LENGTH_SHORT).show()
            }
            getwardenCoroutineScope.cancel()
        }
    }

}