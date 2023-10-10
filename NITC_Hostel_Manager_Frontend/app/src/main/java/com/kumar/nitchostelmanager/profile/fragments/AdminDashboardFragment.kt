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
import com.kumar.nitchostelmanager.students.access.ManageStudentAccess
import com.kumar.nitchostelmanager.databinding.FragmentAdminDashboardBinding
import com.kumar.nitchostelmanager.hostels.access.ManageHostelsAccess
import com.kumar.nitchostelmanager.hostels.adapters.HostelListAdapter
import com.kumar.nitchostelmanager.notice.access.NoticeAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import com.kumar.nitchostelmanager.wardens.access.ManageWardensAccess
import com.kumar.nitchostelmanager.wardens.access.WardensDataAccess
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
        getHostels()
        getWardensCount()

        binding.totalWardensCardInAdminDashboard.setOnClickListener {
            findNavController().navigate(R.id.wardenListFragment)
        }
        binding.totalStudentsCardInAdminDashboard.setOnClickListener {
            findNavController().navigate(R.id.allStudentsFragment)
        }
        binding.swipeRefreshLayoutInAdminDashboard.setOnRefreshListener {

            getHostels()
            getWardensCount()
            getStudentsCount()
            getTotalComplaints()
            getTotalNotices()
            binding.swipeRefreshLayoutInAdminDashboard.isRefreshing = false
        }


        binding.addHostelsButtonInAdminDashboard.setOnClickListener {
            findNavController().navigate(R.id.addHostelFragment)
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

    private fun getWardensCount() {
        var getWardensCoroutineScope = CoroutineScope(Dispatchers.Main)
        getWardensCoroutineScope.launch {
            var wardensCount = WardensDataAccess(
                requireContext(),
                profileViewModel.loginToken.toString()
            ).getWardensCount(binding.parentLayoutInAdminDashboard)
            getWardensCoroutineScope.cancel()
            binding.totalWardensTextInAdminDashboard.text = wardensCount.toString()
        }
    }

    private fun getHostels() {
        var getwardenCoroutineScope = CoroutineScope(Dispatchers.Main)
        getwardenCoroutineScope.launch {
            var hostels = ManageHostelsAccess(
                requireContext(),
                profileViewModel.loginToken.toString(),
                this@AdminDashboardFragment
            ).getHostels()
            if(!hostels.isNullOrEmpty()){
                binding.addHostelsButtonInAdminDashboard.visibility = View.GONE
                binding.hostelsRecyclerViewInAdminDashboard.visibility = View.VISIBLE
                binding.hostelsRecyclerViewInAdminDashboard.layoutManager = LinearLayoutManager(context)
                binding.hostelsRecyclerViewInAdminDashboard.adapter = HostelListAdapter(
                    requireContext(),
                    this@AdminDashboardFragment,
                    sharedViewModel,
                    hostels
                )
            }else{
                binding.hostelsRecyclerViewInAdminDashboard.visibility = View.GONE
                binding.addHostelsButtonInAdminDashboard.visibility = View.VISIBLE
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
                binding.totalStudentsTextInAdminDashboard.text = ((studentsCount["BoysCount"]!! + studentsCount["GirlsCount"]!!)).toString()
            }else{
//                Toast.makeText(requireContext(),"Error in getting students count", Toast.LENGTH_SHORT).show()
            }
            studentsCountCoroutineScope.cancel()
        }
    }
}