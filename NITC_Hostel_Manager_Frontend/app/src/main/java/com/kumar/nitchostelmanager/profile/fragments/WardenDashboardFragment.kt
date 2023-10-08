package com.kumar.nitchostelmanager.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.LocalStorageAccess
import com.kumar.nitchostelmanager.profile.access.ProfileAccess
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.complaints.access.ComplaintsDataAccess
import com.kumar.nitchostelmanager.databinding.FragmentWardenDashboardBinding
import com.kumar.nitchostelmanager.hostels.access.HostelDataAccess
import com.kumar.nitchostelmanager.notice.access.NoticeAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class WardenDashboardFragment:Fragment(),CircleLoadingDialog{

    private lateinit var binding: FragmentWardenDashboardBinding
    private val profileViewModel:ProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWardenDashboardBinding.inflate(inflater,container,false)

        getProfile()
        getStudentsCount()
        getNoticesCount()
        getComplaintsCount()
        binding.logoutButtonInWardenDashboard.setOnClickListener {
            var deleted = LocalStorageAccess(
                this@WardenDashboardFragment,
                requireContext(),
                profileViewModel
            ).deleteData()
        }
        binding.swipeRefreshLayoutInWardenDashboard.setOnRefreshListener {
            getProfile()
            getStudentsCount()
            getNoticesCount()
            getComplaintsCount()
            binding.swipeRefreshLayoutInWardenDashboard.isRefreshing = false
        }


        val backCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)
        return binding.root
    }

    private fun getComplaintsCount(){
        var complaintsCoroutineScope = CoroutineScope(Dispatchers.Main)
        complaintsCoroutineScope.launch {
            var loadingDialog = getLoadingDialog(requireContext(),this@WardenDashboardFragment)
            loadingDialog.create()
            loadingDialog.show()
            var complaintsCount = ComplaintsDataAccess(
                requireContext(),
                this@WardenDashboardFragment,
                profileViewModel.loginToken.toString()
            ).getComplaintsCount()
            loadingDialog.cancel()
            complaintsCoroutineScope.cancel()
            binding.totalComplaintsTextInWardenDashboard.text = complaintsCount.toString()
        }
    }
    private fun getNoticesCount() {
        var noticesCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        noticesCountCoroutineScope.launch {
            var loadingDialog = getLoadingDialog(requireContext(),this@WardenDashboardFragment)
            loadingDialog.create()
            loadingDialog.show()
            var noticesCount = NoticeAccess(
                profileViewModel,
                requireContext()
            ).getNoticesCount()
            binding.totalNoticesTextInWardenDashboard.text = noticesCount.toString()
            loadingDialog.cancel()
            noticesCountCoroutineScope.cancel()
        }
    }
    private fun getStudentsCount() {
        val studentsCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        studentsCountCoroutineScope.launch {
            var loadingDialog = getLoadingDialog(requireContext(),this@WardenDashboardFragment)
            loadingDialog.create()
            loadingDialog.show()
            var studentsCount = HostelDataAccess(
                requireContext(),
                this@WardenDashboardFragment,
                profileViewModel.loginToken.toString()
            ).getHostelOccupantsCount()
            loadingDialog.cancel()
            studentsCountCoroutineScope.cancel()
            if(studentsCount>=0){
                binding.totalStudentsTextInWardenDashboard.text = studentsCount.toString()
            }
        }
    }

    private fun getProfile() {
        val getProfileCoroutineScope = CoroutineScope(Dispatchers.Main)
        getProfileCoroutineScope.launch {
            var loadingDialog = getLoadingDialog(requireContext(),this@WardenDashboardFragment)
            loadingDialog.create()
            loadingDialog.show()
            val warden = ProfileAccess(requireContext(),profileViewModel).getWardenProfile()
            loadingDialog.cancel()
            getProfileCoroutineScope.cancel()
            if(warden != null){
                binding.nameTextInWardenDashboard.text = warden.name.toString()
                binding.emailTextInWardenDashboard.text = warden.email.toString()
                profileViewModel.currentWarden = warden
            }else{
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }

}