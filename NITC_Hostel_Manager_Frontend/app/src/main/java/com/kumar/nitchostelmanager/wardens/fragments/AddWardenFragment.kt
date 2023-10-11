package com.kumar.nitchostelmanager.wardens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentAddStudentBinding
import com.kumar.nitchostelmanager.databinding.FragmentAddWardenBinding
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.wardens.access.ManageWardensAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AddWardenFragment:Fragment(),CircleLoadingDialog {
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private lateinit var binding:FragmentAddWardenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWardenBinding.inflate(inflater,container,false)

        binding.buttonAddWardenInAddWardenFragment.setOnClickListener {
            var wardenName = binding.textInputNameInAddWardenFragment.text?.trim().toString()
            if(wardenName.isEmpty()){
                binding.textInputNameInAddWardenFragment.error = "Enter name"
                binding.textInputNameInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }
            var wardenEmail = binding.textInputEmailInAddWardenFragment.text?.trim().toString()
            if(wardenEmail.isEmpty()){
                binding.textInputEmailInAddWardenFragment.error = "Enter email"
                binding.textInputEmailInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }
            var wardenPhone = binding.textInputPhoneInAddWardenFragment.text?.trim().toString()
            if(wardenPhone.isEmpty()){
                binding.textInputPhoneInAddWardenFragment.error = "Enter name"
                binding.textInputPhoneInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }
            var wardenGender = binding.textInputGenderInAddWardenFragment.text?.trim().toString()
            if(wardenGender.isEmpty()){
                binding.textInputGenderInAddWardenFragment.error = "Enter name"
                binding.textInputGenderInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }
            var wardenHostelID = binding.hostelInputInAddWardenFragment.text?.trim().toString()
            if(wardenHostelID.isEmpty()){
                binding.hostelInputInAddWardenFragment.error = "Enter name"
                binding.hostelInputInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }
            var newWarden = Warden(
                wardenEmail,
                wardenEmail,
                wardenName,
                wardenPhone,
                wardenGender,
                wardenHostelID
            )
            addWarden(newWarden)

        }

        return binding.root
    }

    private fun addWarden(newWarden: Warden) {
        val addWardenCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AddWardenFragment)
        addWardenCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val added = ManageWardensAccess(
                requireContext(),
                this@AddWardenFragment,
                profileViewModel
            ).addWarden(newWarden)
            loadingDialog.cancel()
            addWardenCoroutineScope.cancel()
            if(added){
                findNavController().navigate(R.id.wardenListFragment)
            }
        }
    }
}