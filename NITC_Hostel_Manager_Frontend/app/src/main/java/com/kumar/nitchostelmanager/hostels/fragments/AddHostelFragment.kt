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
import com.kumar.nitchostelmanager.hostels.access.ManageHostelsAccess
import com.kumar.nitchostelmanager.models.Hostel
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddHostelFragment:Fragment(),CircleLoadingDialog {

    private val profileViewModel:ProfileViewModel by activityViewModels()
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

        binding.addHostelButtonInAddHostelFragment.setOnClickListener {
            val hostelName = binding.hostelNameInputInAddHostelFragment.text?.trim().toString()
            if(hostelName.isEmpty()){
                binding.hostelNameInputInAddHostelFragment.error = "Enter hostel name"
                binding.hostelNameInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }
            val occupantsGender = binding.occupantsGenderInputInAddHostelFragment.text?.trim().toString()
            if(occupantsGender.isEmpty()){
                binding.occupantsGenderInputInAddHostelFragment.error = "Enter hostel name"
                binding.occupantsGenderInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }
            val chargesString = binding.chargesInputInAddHostelFragment.text?.trim().toString()
            if(chargesString.isEmpty()){
                binding.chargesInputInAddHostelFragment.error = "Enter hostel name"
                binding.chargesInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }

            val capacityString = binding.capacityInputInAddHostelFragment.text?.trim().toString()
            if(capacityString.isEmpty()){
                binding.capacityInputInAddHostelFragment.error = "Enter hostel name"
                binding.capacityInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }
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
                occupantsGender,
                null
            )
            addHostel(newHostel)
        }

        return binding.root
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