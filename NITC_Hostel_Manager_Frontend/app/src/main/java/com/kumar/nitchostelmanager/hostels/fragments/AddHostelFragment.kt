package com.kumar.nitchostelmanager.hostels.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentAddHostelBinding
import com.kumar.nitchostelmanager.hostels.access.HostelDataAccess
import com.kumar.nitchostelmanager.hostels.access.ManageHostelsAccess
import com.kumar.nitchostelmanager.models.Hostel
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddHostelFragment:Fragment(),CircleLoadingDialog {

    private val profileViewModel:ProfileViewModel by activityViewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private lateinit var binding:FragmentAddHostelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddHostelBinding.inflate(inflater,container,false)

        binding.buttonClearAllInAddHostelFragment.setOnClickListener {
            clearAll()
        }

        if(sharedViewModel.updatingHostelID != null){
            binding.hostelNameInputInAddHostelFragment.isEnabled = false
            binding.wardenEmailInputCardInAddHostelFragment.visibility = View.VISIBLE
            getData(sharedViewModel.updatingHostelID.toString())
        }

        binding.addHostelButtonInAddHostelFragment.setOnClickListener {
            val hostelName = binding.hostelNameInputInAddHostelFragment.text?.trim().toString()
            if(hostelName.isEmpty()){
                binding.hostelNameInputInAddHostelFragment.error = "Enter hostel name"
                binding.hostelNameInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }

            val occupantsGender = binding.occupantsGenderInputInAddHostelFragment.text?.trim().toString()
            if(occupantsGender.isEmpty()){
                binding.occupantsGenderInputInAddHostelFragment.error = "Enter gender"
                binding.occupantsGenderInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }

            val chargesString = binding.chargesInputInAddHostelFragment.text?.trim().toString()
            if(chargesString.isEmpty()){
                binding.chargesInputInAddHostelFragment.error = "Enter hostel charge"
                binding.chargesInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }

            val capacityString = binding.capacityInputInAddHostelFragment.text?.trim().toString()
            if(capacityString.isEmpty()){
                binding.capacityInputInAddHostelFragment.error = "Enter hostel capacity"
                binding.capacityInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }

            val wardenEmail = binding.wardenEmailInputInAddHostelFragment.text?.trim().toString()

            val charges = chargesString.toInt()
            val capacity = capacityString.toInt()
            if(charges<=0){
                binding.chargesInputInAddHostelFragment.error = "Hostel Charges should be greater than 0"
                binding.chargesInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }
            if(capacity<=0){
                binding.capacityInputInAddHostelFragment.error = "Hostel Capacity should be greater than 0"
                binding.capacityInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }

            var newHostel = Hostel(
                hostelName,
                capacity,
                charges,
                0.0,
                0,
                occupantsGender,
                null
            )

            if(sharedViewModel.updatingHostelID != null) updateHostel(newHostel, wardenEmail)
            else addHostel(newHostel)
        }

        return binding.root
    }

    private fun updateHostel(newHostel: Hostel, wardenEmail: String) {
        newHostel.wardenEmail = wardenEmail

        val updateHostelCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AddHostelFragment)

        updateHostelCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val added = ManageHostelsAccess(
                requireContext(),
                profileViewModel.loginToken.toString(),
                this@AddHostelFragment
            ).updateHostel(newHostel, sharedViewModel.updatingHostelID.toString())
            loadingDialog.cancel()
            if(added){
                getData(newHostel.hostelID)
            }
        }
    }

    private fun getData(hostelID:String) {
        val getHostelCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AddHostelFragment)
        getHostelCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val hostel = HostelDataAccess(
                requireContext(),
                this@AddHostelFragment,
                profileViewModel.loginToken.toString()
            ).getHostelDetails(hostelID)
            loadingDialog.cancel()
            if(hostel != null){
                binding.wardenEmailInputInAddHostelFragment.setText(hostel.wardenEmail.toString())
                binding.chargesInputInAddHostelFragment.setText(hostel.charges.toString())
                binding.capacityInputInAddHostelFragment.setText(hostel.capacity.toString())
                binding.hostelNameInputInAddHostelFragment.setText(hostel.hostelID.toString())
                binding.addHostelButtonInAddHostelFragment.setText("Update Hostel")
                binding.occupantsGenderInputInAddHostelFragment.setText(hostel.occupantsGender.toString())
                binding.headingInAddHostelFragment.setText("Update Hostel")
            }
        }
    }

    private fun clearAll() {
        binding.hostelNameInputInAddHostelFragment.setText("")
        binding.chargesInputInAddHostelFragment.setText("")
        binding.capacityInputInAddHostelFragment.setText("")
        binding.occupantsGenderInputInAddHostelFragment.setText("")
    }

    private fun addHostel(newHostel:Hostel) {
        val addHostelCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AddHostelFragment)
        loadingDialog.setCancelable(true)
        addHostelCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val added = ManageHostelsAccess(
                requireContext(),
                profileViewModel.loginToken.toString(),
                this@AddHostelFragment
            ).addHostel(newHostel)
            loadingDialog.cancel()
            if(added){
                clearAll()
            }
        }
    }

}