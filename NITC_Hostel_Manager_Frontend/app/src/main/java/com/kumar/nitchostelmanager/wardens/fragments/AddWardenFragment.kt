package com.kumar.nitchostelmanager.wardens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.Validation
import com.kumar.nitchostelmanager.databinding.FragmentAddStudentBinding
import com.kumar.nitchostelmanager.databinding.FragmentAddWardenBinding
import com.kumar.nitchostelmanager.hostels.access.HostelDataAccess
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.profile.access.ProfileAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import com.kumar.nitchostelmanager.wardens.access.ManageWardensAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AddWardenFragment:Fragment(),CircleLoadingDialog, Validation {
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding:FragmentAddWardenBinding
    var genderSelected: String = "Male"
    var hostelNames : Array<String>? = null
    var hostelSelected:Int = -1
    var warden:Warden? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWardenBinding.inflate(inflater,container,false)

        if(sharedViewModel.viewingWardenEmail != null){
            binding.headingInAddWardenFragment.text = "Update Warden"
            binding.buttonAddWardenInAddWardenFragment.text = "Update Warden"
            getWardenData(sharedViewModel.viewingWardenEmail.toString())
        }


        binding.hostelNameButtonInAddWardenFragment.setOnClickListener {
            getHostels()
        }
        binding.buttonAddWardenInAddWardenFragment.setOnClickListener {
            var wardenName = binding.textInputNameInAddWardenFragment.text?.trim().toString()
            if(wardenName.isEmpty()){
                binding.textInputNameInAddWardenFragment.error = "Enter Name"
                binding.textInputNameInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }
            else if(!checkValidName(wardenName)){
                binding.textInputNameInAddWardenFragment.error = "Enter Valid Name"
                binding.textInputNameInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }

            var wardenEmail = binding.textInputEmailInAddWardenFragment.text?.trim().toString()
            if(wardenEmail.isEmpty()){
                binding.textInputEmailInAddWardenFragment.error = "Enter Email"
                binding.textInputEmailInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }
            else if(!checkValidEmail(wardenEmail)){
                binding.textInputEmailInAddWardenFragment.error = "Enter Valid Email"
                binding.textInputEmailInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }

            var wardenPhone = binding.textInputPhoneInAddWardenFragment.text?.trim().toString()
            if(wardenPhone.isEmpty()){
                binding.textInputPhoneInAddWardenFragment.error = "Enter Phone Number"
                binding.textInputPhoneInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }
            else if(!checkValidPhoneNumber(wardenPhone)){
                binding.textInputPhoneInAddWardenFragment.error = "Enter Valid Phone Number"
                binding.textInputPhoneInAddWardenFragment.requestFocus()
                return@setOnClickListener
            }

            genderSelected = (requireActivity().findViewById<RadioButton>(binding.genderRadioGroupInAddWardenFragment.checkedRadioButtonId)).text.toString()

            if(hostelSelected == -1){
                Toast.makeText(context,"Select hostel first",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var newWarden = Warden(
                wardenEmail,
                wardenEmail,
                wardenName,
                wardenPhone,
                genderSelected,
                hostelNames!![hostelSelected]
            )

            if(sharedViewModel.viewingWardenEmail != null) {
                var updateWarden = Warden(
                    email = wardenEmail,
                    name = wardenName,
                    phone = wardenPhone,
                    gender = genderSelected,
                    hostelID = hostelNames!![hostelSelected]
                )
                updateWarden(sharedViewModel.viewingWardenEmail.toString(), updateWarden,warden!!.hostelID)
            }
                else
                    addWarden(newWarden)
        }

        binding.buttonClearAllInAddWardenFragment.setOnClickListener {
            clearAllTextArea()
        }

        val backCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                sharedViewModel.viewingWardenEmail = null
                findNavController().navigate(R.id.wardenListFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)

        return binding.root
    }

    private fun getHostels(){
        val hostelCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AddWardenFragment)
        hostelCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            hostelNames = HostelDataAccess(
                requireContext(),
                this@AddWardenFragment,
                profileViewModel.loginToken!!
            ).getHostelNames(genderSelected)
            loadingDialog.cancel()
            hostelCoroutineScope.cancel()
            if(!hostelNames.isNullOrEmpty()){
                hostelSelected = -1
                AlertDialog.Builder(requireContext())
                    .setTitle("Choose Hostel")
                    .setSingleChoiceItems(hostelNames,hostelSelected){dialog,which->
                        hostelSelected = which
                    }
                    .setPositiveButton("Select"){dialog,which->
                        binding.hostelNameButtonInAddWardenFragment.text = hostelNames!![hostelSelected]
                        dialog.dismiss()
                    }
                    .setNegativeButton("No"){dialog,which->
//                        if(hostelSelected != -1) binding.hostelNameButtonInAddWardenFragment.text = hostelNames!![hostelSelected]
                        hostelSelected = -1
                        binding.hostelNameButtonInAddWardenFragment.text = "Hostel Name"
                        dialog.dismiss()
                    }
                    .create().show()
            }else{
                Toast.makeText(context,"No Hostels found. Add some hostels first",Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun getWardenData(wardenEmail: String){
        val getWardenCoroutineScope = CoroutineScope(Dispatchers.Main)
        getWardenCoroutineScope.launch {
            var loadingDialog = getLoadingDialog(requireContext(),this@AddWardenFragment)
            loadingDialog.create()
            loadingDialog.show()
            warden = ManageWardensAccess(requireContext(), this@AddWardenFragment, profileViewModel)
                .getWardenDetails(wardenEmail)
            loadingDialog.cancel()
            getWardenCoroutineScope.cancel()

            if(warden != null){
                binding.textInputNameInAddWardenFragment.setText(warden!!.name)
                binding.textInputEmailInAddWardenFragment.setText(warden!!.email)
                binding.textInputPhoneInAddWardenFragment.setText(warden!!.phone)
                binding.hostelNameButtonInAddWardenFragment.setText(warden!!.hostelID)

                if(warden!!.gender == "Male") {
                    binding.checkBoxMaleInAddWardenFragment.isChecked = true
                    binding.checkBoxFemaleInAddWardenFragment.isChecked = false
                }
                else {
                    binding.checkBoxFemaleInAddWardenFragment.isChecked = true
                    binding.checkBoxMaleInAddWardenFragment.isChecked = false
                }
            }else{
                findNavController().navigate(R.id.adminDashboardFragment)
            }
        }
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

    private fun updateWarden(wardenEmail: String, newWarden: Warden,hostelID:String){
        val updateWardenCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(), this@AddWardenFragment)

        updateWardenCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()

            val updatesWarden = ManageWardensAccess(requireContext(), this@AddWardenFragment, profileViewModel)
                .updateWarden(wardenEmail, newWarden, hostelID)

            loadingDialog.cancel()
            updateWardenCoroutineScope.cancel()

            if(updatesWarden) getWardenData(wardenEmail)
        }
    }

    private fun clearAllTextArea(){
        binding.textInputNameInAddWardenFragment.setText("")
        binding.textInputEmailInAddWardenFragment.setText("")
        binding.textInputPhoneInAddWardenFragment.setText("")
        binding.hostelNameButtonInAddWardenFragment.setText("Select hostel")
        hostelSelected = -1
        genderSelected = "NA"
    }
}