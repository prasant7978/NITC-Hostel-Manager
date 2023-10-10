package com.kumar.nitchostelmanager.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.LocalStorageAccess
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentStudentDashboardBinding
import com.kumar.nitchostelmanager.profile.access.ProfileAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentDashboardFragment:Fragment() ,CircleLoadingDialog{
    private lateinit var binding:FragmentStudentDashboardBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDashboardBinding.inflate(inflater,container,false)

        getStudentProfile()

        binding.studentProfileBoxInStudentDashboard.setOnClickListener {
            findNavController().navigate(R.id.studentProfileFragment)
        }

        binding.noticeBoxInStudentDashboard.setOnClickListener {
            findNavController().navigate(R.id.noticeListFragment)
        }

        binding.complaintBoxInStudentDashboard.setOnClickListener {
            findNavController().navigate(R.id.viewOwnComplaintsFragment)
        }

        binding.paymentBoxInStudentDashboard.setOnClickListener {
            findNavController().navigate(R.id.paymentFragment)
        }

        binding.paymentHistoryBoxInStudentDashboard.setOnClickListener {
            findNavController().navigate(R.id.studentPaymentHistoryFragment)
        }

        binding.roomBoxInStudentDashboard.setOnClickListener {
            findNavController().navigate(R.id.changeRoomFragment)
        }

        binding.hostelBoxInStudentDashboard.setOnClickListener {
            findNavController().navigate(R.id.viewHostelFragment)
        }

        binding.logoutButtonInStudentDashboard.setOnClickListener {
            var deleted = LocalStorageAccess(
                this@StudentDashboardFragment,
                requireContext(),
                profileViewModel
            ).deleteData()

            findNavController().navigate(R.id.loginFragment)
        }

        return binding.root
    }

    private fun getStudentProfile(){
        val profileCoroutineScope = CoroutineScope(Dispatchers.Main)
        profileCoroutineScope.launch {
            val studentProfile = ProfileAccess(requireContext(), profileViewModel).getStudentProfile()

            if(studentProfile != null){
                binding.studentNameInStudentDashboard.text = studentProfile.name
                binding.studentRollInStudentDashboard.text = studentProfile.studentRoll

                if(studentProfile.hostelID != null)
                    binding.hostelNameInStudentDashboard.text = studentProfile.hostelID
                else
                    binding.hostelNameInStudentDashboard.text = "NA"

                if(studentProfile.roomNumber != null)
                    binding.roomNumberInStudentDashboard.text = studentProfile.roomNumber
                else
                    binding.hostelNameInStudentDashboard.text = "NA"
            }
            else{
                binding.studentNameInStudentDashboard.text = ""
                binding.studentRollInStudentDashboard.text = ""
                binding.hostelNameInStudentDashboard.text = ""
                binding.roomNumberInStudentDashboard.text = ""
            }
        }
    }
}