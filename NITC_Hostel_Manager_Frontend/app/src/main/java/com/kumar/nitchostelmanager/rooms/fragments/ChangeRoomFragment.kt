package com.kumar.nitchostelmanager.rooms.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentChangeRoomBinding
import com.kumar.nitchostelmanager.hostels.access.HostelDataAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ChangeRoomFragment : Fragment() , AdapterView.OnItemSelectedListener{

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private lateinit var binding:FragmentChangeRoomBinding
    var hostelNames:Array<String>? = null
    var hostelSelected = "NA"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeRoomBinding.inflate(inflater,container,false)

        getHostels()

        binding.searchRoomsButtonInChangeRoomFragment.setOnClickListener {
            sharedViewModel.viewingHostelID = hostelSelected
            Log.d("hostelSelected",hostelSelected)
            findNavController().navigate(R.id.availableRoomsFragment)
        }

        val backCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.studentDashboardFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)

        return binding.root
    }

    private fun getHostels() {
        val getNamesCoroutineScope = CoroutineScope(Dispatchers.Main)
        getNamesCoroutineScope.launch {
            hostelNames = HostelDataAccess(
                requireContext(),
                this@ChangeRoomFragment,
                profileViewModel.loginToken.toString()
            ).getHostelNames()
            getNamesCoroutineScope.cancel()
            if(!hostelNames.isNullOrEmpty()){
                binding.spinnerInChangeRoomFragment.onItemSelectedListener = this@ChangeRoomFragment
                binding.spinnerInChangeRoomFragment.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    hostelNames!!
                )
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(!hostelNames.isNullOrEmpty()) hostelSelected = hostelNames?.get(position).toString()
        else Toast.makeText(context,"Hostel names are empty",Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}