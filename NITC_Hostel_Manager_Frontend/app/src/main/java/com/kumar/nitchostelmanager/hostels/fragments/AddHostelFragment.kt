package com.kumar.nitchostelmanager.hostels.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    var genderSelected = "Male"
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
//        genderList = resources.getStringArray(R.array.gender)
//        binding.genderButtonInAddHostelFragment.setOnClickListener {
//            getGender()
//        }
        binding.addHostelButtonInAddHostelFragment.setOnClickListener {
            val hostelName = binding.hostelNameInputInAddHostelFragment.text?.trim().toString()
            if(hostelName.isEmpty()){
                binding.hostelNameInputInAddHostelFragment.error = "Enter hostel name"
                binding.hostelNameInputInAddHostelFragment.requestFocus()
                return@setOnClickListener
            }

            genderSelected = (requireActivity().findViewById<RadioButton>(binding.genderRadioGroupInAddHostelFragment.checkedRadioButtonId)).text.toString()
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
                genderSelected,
                null
            )

            if(sharedViewModel.updatingHostelID != null) updateHostel(newHostel, wardenEmail)
            else addHostel(newHostel)
        }

        return binding.root
    }
//
//    private fun getGender() {
//        AlertDialog.Builder(requireContext())
//            .setTitle("Choose Gender")
//            .setSingleChoiceItems(genderList!!,-1){dialog,selected->
//                genderSelected = genderList!![selected]
//            }
//            .setPositiveButton("Select"){dialog,which->
//                if(genderSelected != "NA"){
//                    binding.genderButtonInAddHostelFragment.text = genderSelected
//                    dialog.dismiss()
//                }else Toast.makeText(context,"Select Gender", Toast.LENGTH_SHORT).show()
//            }
//            .setNegativeButton("No"){dialog,which->
//                binding.genderButtonInAddHostelFragment.text = genderSelected
//                dialog.dismiss()
//            }
//            .create().show()
//    }

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
                if(hostel.occupantsGender.equals("Male")){
                    binding.checkBoxMaleInAddHostelFragment.isChecked = true
                }else binding.checkBoxFemaleInAddHostelFragment.isChecked = true
                binding.headingInAddHostelFragment.setText("Update Hostel")
                binding.capacityInputInAddHostelFragment.isEnabled = false
            }
        }
    }

    private fun clearAll() {
        binding.hostelNameInputInAddHostelFragment.setText("")
        binding.chargesInputInAddHostelFragment.setText("")
        binding.capacityInputInAddHostelFragment.setText("")
        binding.checkBoxMaleInAddHostelFragment.isChecked = true
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