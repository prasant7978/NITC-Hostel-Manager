package com.kumar.nitchostelmanager.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.LocalStorageAccess
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.complaints.access.ComplaintsDataAccess
import com.kumar.nitchostelmanager.complaints.access.ManageComplaintAccess
import com.kumar.nitchostelmanager.students.access.ManageStudentAccess
import com.kumar.nitchostelmanager.wardens.access.ManageWardensAccess
import com.kumar.nitchostelmanager.wardens.adapter.WardenListAdapter
import com.kumar.nitchostelmanager.databinding.FragmentAdminDashboardBinding
import com.kumar.nitchostelmanager.notice.access.NoticeAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AdminDashboardFragment:Fragment(),CircleLoadingDialog {
    private lateinit var binding:FragmentAdminDashboardBinding
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(inflater,container,false)

        getStudentsCount()
        getTotalComplaints()
        getTotalNotices()
        getWardens()
        binding.swipeRefreshLayoutInAdminDashboard.setOnRefreshListener {

            getStudentsCount()
            getTotalComplaints()
            getTotalNotices()
            binding.swipeRefreshLayoutInAdminDashboard.isRefreshing = false
        }

        binding.logoutButtonInWardenDashboard.setOnClickListener {
            var deleted = LocalStorageAccess(
                this@AdminDashboardFragment,
                requireContext(),
                profileViewModel
            ).deleteData()
            findNavController().navigate(R.id.loginFragment)
        }
        return binding.root
    }

    private fun getWardens() {
        var getwardenCoroutineScope = CoroutineScope(Dispatchers.Main)
        getwardenCoroutineScope.launch {
            var wardens = ManageWardensAccess(
                requireContext(),
                this@AdminDashboardFragment,
                profileViewModel
            ).getWardens(binding.parentLayoutInAdminDashboard)
            if(!wardens.isNullOrEmpty()){
                binding.wardensRecyclerViewInAdminDashboard.layoutManager = LinearLayoutManager(context)
                binding.wardensRecyclerViewInAdminDashboard.adapter = WardenListAdapter(
                    wardens,
                    sharedViewModel,
                    this@AdminDashboardFragment
                )
            }else{
                Toast.makeText(context,"No wardens till now",Toast.LENGTH_SHORT).show()
            }
            getwardenCoroutineScope.cancel()
        }
    }

    private fun getTotalNotices() {
        var noticesCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        noticesCountCoroutineScope.launch {
            var noticesCount = NoticeAccess(
                profileViewModel,
                requireContext()
            ).getNoticesCount()
            binding.noticesTextInAdminDashboard.text = noticesCount.toString()
            noticesCountCoroutineScope.cancel()
        }
    }

    private fun getTotalComplaints() {
        var complaintsCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        complaintsCountCoroutineScope.launch {
            var complaintsCount = ComplaintsDataAccess(
                requireContext(),
                this@AdminDashboardFragment,
                profileViewModel.loginToken.toString()
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
            ).getAllStudentsCount()
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