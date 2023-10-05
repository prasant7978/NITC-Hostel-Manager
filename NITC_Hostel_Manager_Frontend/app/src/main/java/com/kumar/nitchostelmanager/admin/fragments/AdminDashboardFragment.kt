package com.kumar.nitchostelmanager.admin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kumar.nitchostelmanager.ManageComplaintAccess
import com.kumar.nitchostelmanager.ManageNoticesAccess
import com.kumar.nitchostelmanager.admin.access.ManageStudentAccess
import com.kumar.nitchostelmanager.databinding.FragmentAdminDashboardBinding
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AdminDashboardFragment:Fragment() {
    private lateinit var binding:FragmentAdminDashboardBinding
    private val profileViewModel:ProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(inflater,container,false)

        getStudentsCount()
        getTotalComplaints()
        getTotalNotices()

        binding.swipeRefreshLayoutInAdminDashboard.setOnRefreshListener {

            getStudentsCount()
            getTotalComplaints()
            getTotalNotices()
            binding.swipeRefreshLayoutInAdminDashboard.isRefreshing = false
        }


        return binding.root
    }

    private fun getTotalNotices() {
        var noticesCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        noticesCountCoroutineScope.launch {
            var noticesCount = ManageNoticesAccess(requireContext(),
                profileViewModel.loginToken.toString()
            ).getNoticesCount()
            binding.noticesTextInAdminDashboard.text = noticesCount.toString()
            noticesCountCoroutineScope.cancel()
        }
    }

    private fun getTotalComplaints() {
        var complaintsCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        complaintsCountCoroutineScope.launch {
            var complaintsCount = ManageComplaintAccess(requireContext(),
                profileViewModel = profileViewModel
            ).getComplaintsCount()
                binding.complaintsTextInAdminDashboard.text = complaintsCount.toString()
            complaintsCountCoroutineScope.cancel()
        }
    }

    private fun getStudentsCount() {
        var studentsCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        studentsCountCoroutineScope.launch {
            var studentsCount = ManageStudentAccess(requireContext(),
                this@AdminDashboardFragment,
                profileViewModel = profileViewModel
            ).getStudentsCount()
            if(studentsCount != null){
                binding.totalBoysTextInAdminDashboard.text = studentsCount["BoysCount"].toString()
                binding.totalGirlsTextInAdminDashboard.text = studentsCount["GirlsCount"].toString()
            }else{
//                Toast.makeText(requireContext(),"Error in getting students count", Toast.LENGTH_SHORT).show()
            }
            studentsCountCoroutineScope.cancel()
        }
    }
}