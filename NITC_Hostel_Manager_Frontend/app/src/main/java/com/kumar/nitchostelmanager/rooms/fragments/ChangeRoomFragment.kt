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
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentChangeRoomBinding
import com.kumar.nitchostelmanager.hostels.access.HostelDataAccess
import com.kumar.nitchostelmanager.models.Hostel
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ChangeRoomFragment : Fragment() , AdapterView.OnItemSelectedListener, CircleLoadingDialog{

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private lateinit var binding:FragmentChangeRoomBinding
    var hostels:ArrayList<Hostel>? = arrayListOf()
    var hostelSelected = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeRoomBinding.inflate(inflater,container,false)

        getHostels(profileViewModel.currentStudent.gender.toString())

        binding.searchRoomsButtonInChangeRoomFragment.setOnClickListener {
            if(!hostels.isNullOrEmpty() && hostels!!.size>hostelSelected) sharedViewModel.viewingHostel = hostels!![hostelSelected]
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

    private fun getHostels(gender:String) {
        val getNamesCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@ChangeRoomFragment)

        getNamesCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()

            hostels = HostelDataAccess(
                requireContext(),
                this@ChangeRoomFragment,
                profileViewModel.loginToken.toString()
            ).getHostels(gender)

            loadingDialog.cancel()
            getNamesCoroutineScope.cancel()

            if(!hostels.isNullOrEmpty()){
                binding.spinnerInChangeRoomFragment.onItemSelectedListener = this@ChangeRoomFragment
                binding.spinnerInChangeRoomFragment.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    hostels!!.map{it.hostelID}
                )
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(!hostels.isNullOrEmpty()) hostelSelected = position
        else Toast.makeText(context,"Hostel names are empty",Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}