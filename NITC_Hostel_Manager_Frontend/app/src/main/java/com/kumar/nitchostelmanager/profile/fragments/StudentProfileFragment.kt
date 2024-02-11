package com.kumar.nitchostelmanager.profile.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentStudentProfileBinding
import com.kumar.nitchostelmanager.profile.access.ProfileAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.ViewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class StudentProfileFragment : Fragment(),CircleLoadingDialog {
    private lateinit var binding:FragmentStudentProfileBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val viewsViewModel:ViewsViewModel by activityViewModels()
    var expanded:Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_student_profile,container,false)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewsViewModel
        viewsViewModel.updateLoadingState(true)

        getAndSetProfileDetails()
        binding.changePasswordConstraintLayout.visibility = View.GONE

        binding.changePasswordTextViewInStudentProfileFragment.setOnClickListener {
            expanded = !expanded
            if(expanded) binding.changePasswordConstraintLayout.visibility = View.VISIBLE
            else binding.changePasswordConstraintLayout.visibility = View.GONE
        }

        binding.savePasswordButtonInStudentProfile.setOnClickListener {
            val newpassword = binding.newPasswordInStudentProfileFragment.text.toString()
            if(newpassword.isEmpty()){
                binding.newPasswordInStudentProfileFragment.error = "Enter The New Password"
                binding.newPasswordInStudentProfileFragment.requestFocus()
                return@setOnClickListener
            }
            else if(newpassword.length < 8){
                binding.newPasswordInStudentProfileFragment.error = "Password must be at least 8 characters long"
                binding.newPasswordInStudentProfileFragment.requestFocus()
                return@setOnClickListener
            }

            val confirmpassword = binding.confirmPasswordInStudentProfileFragment.text.toString()
            if(confirmpassword.isEmpty()){
                binding.confirmPasswordInStudentProfileFragment.error = "Enter Confirm Password"
                binding.confirmPasswordInStudentProfileFragment.requestFocus()
                return@setOnClickListener
            }

            if(newpassword != confirmpassword){
                binding.confirmPasswordInStudentProfileFragment.error = "Password don't match"
                binding.confirmPasswordInStudentProfileFragment.requestFocus()
                return@setOnClickListener
            }

            updatePassword(newpassword)
        }

        return binding.root
    }

    private fun updatePassword(newpassword: String) {
        val updatePasswordCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@StudentProfileFragment)
        updatePasswordCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val updated = ProfileAccess(requireContext(),profileViewModel).updatePassword(newpassword)
            loadingDialog.cancel()
            updatePasswordCoroutineScope.cancel()
            if(updated == true){
                expanded = false
                binding.changePasswordConstraintLayout.visibility = View.GONE
                Snackbar.make(binding.mainLayoutInStudentProfileFragment,"Password updated",Snackbar.LENGTH_SHORT).show()
                binding.newPasswordInStudentProfileFragment.setText("")
                binding.confirmPasswordInStudentProfileFragment.setText("")
                getAndSetProfileDetails()
            }
        }
    }

    private fun getAndSetProfileDetails() {
        val profileCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@StudentProfileFragment)
        profileCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            viewsViewModel.updateLoadingState(true)
            val studentProfile = ProfileAccess(requireContext(), profileViewModel).getStudentProfile()
            loadingDialog.cancel()
            profileCoroutineScope.cancel()
            if(studentProfile != null){
                viewsViewModel.updateLoadingState(false)
                binding.studentNameInStudentProfile.text = studentProfile.name
                binding.studentEmailInStudentProfile.text = studentProfile.email
                binding.phoneInStudentProfileFragment.text = studentProfile.phone
                binding.parentPhoneInStudentProfileFragment.text = studentProfile.parentPhone
                binding.addressInStudentProfileFragment.text = studentProfile.address
                binding.genderInStudentProfileFragment.text = studentProfile.gender
                binding.dobInStudentProfileFragment.text = studentProfile.dob
                binding.PasswordInStudentProfileFragment.text = studentProfile.password
                binding.courseInStudentProfileFragment.text = studentProfile.course
                binding.duesInStudentProfileFragment.text = studentProfile.dues.toString()
                binding.studentRollInStudentProfileFragment.text = studentProfile.studentRoll.uppercase()

                var hostel = studentProfile.hostelID
                if(hostel.isNullOrEmpty()) {
                    binding.hostelInStudentProfileFragment.text = "NA"
                    binding.roomInStudentProfileFragment.text = "NA"
                }
                else{
                    binding.hostelInStudentProfileFragment.text = studentProfile.hostelID
                    binding.roomInStudentProfileFragment.text = studentProfile.roomNumber.toString()
                }

                if(studentProfile.gender == "Male"){
                    binding.profileImgInStudentProfile.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.man))
                }
                else{
                    binding.profileImgInStudentProfile.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.woman))
                }
            }else{
                Toast.makeText(context,"Error in loading your details",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.studentDashboardFragment)
            }
        }
    }
}