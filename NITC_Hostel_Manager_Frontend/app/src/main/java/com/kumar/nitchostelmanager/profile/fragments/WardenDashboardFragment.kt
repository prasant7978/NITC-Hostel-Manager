package com.kumar.nitchostelmanager.profile.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
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
import com.kumar.nitchostelmanager.viewModel.ViewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class WardenDashboardFragment:Fragment(),CircleLoadingDialog{
    private lateinit var binding: FragmentWardenDashboardBinding
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private val viewsViewModel:ViewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_warden_dashboard,container,false)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewsViewModel
        viewsViewModel.updateLoadingState(true)
        getProfile()

        binding.swipeRefreshLayoutInWardenDashboard.setOnRefreshListener {
            getProfile()

            binding.swipeRefreshLayoutInWardenDashboard.isRefreshing = false
        }

        binding.changePasswordTextViewInWardenDashboardFragment.setOnClickListener {
            findNavController().navigate(R.id.changePasswordFragment)
        }

        binding.complaintsCardInWardenDashboard.setOnClickListener {
            findNavController().navigate(R.id.viewAllComplaintsFragment)
        }

        binding.logoutButtonInWardenDashboard.setOnClickListener {
            showDialog()
        }

        binding.roomsCardInWardenDashboard.setOnClickListener {
            findNavController().navigate(R.id.availableRoomsFragment)
        }

        binding.noticesCardInWardenDashboard.setOnClickListener {
            findNavController().navigate(R.id.noticeListFragment)
        }

        binding.studentsCardInWardenDashboard.setOnClickListener {
            findNavController().navigate(R.id.occupantsFragment)
        }

        binding.billsBoxInWardenDashboard.setOnClickListener {
            findNavController().navigate(R.id.allBillsFragment)
        }

        val backCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)

        return binding.root
    }

    private fun showDialog(){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Logout")
        dialog?.setMessage("Are you sure you want to log out?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            var deleted = LocalStorageAccess(
                this@WardenDashboardFragment,
                requireContext(),
                profileViewModel
            ).deleteData()
            findNavController().navigate(R.id.loginFragment)
        })
        dialog?.create()?.show()
    }

    private fun getPendingComplaintsCount(loadingDialog: Dialog) {
        var complaintsCoroutineScope = CoroutineScope(Dispatchers.Main)
        complaintsCoroutineScope.launch { 

            var complaintsCount = ComplaintsDataAccess(
                requireContext(),
                this@WardenDashboardFragment,
                profileViewModel.loginToken.toString()
            ).getPendingComplaintsCount(profileViewModel.currentWarden.hostelID)

            viewsViewModel.updateLoadingState(false)
            loadingDialog.cancel()
            complaintsCoroutineScope.cancel()

            binding.totalComplaintsTextInWardenDashboard.text = complaintsCount.toString()
        }
    }
    private fun getNoticesCount() {
        var noticesCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        noticesCountCoroutineScope.launch {
            var loadingDialog = getLoadingDialog(requireContext(),this@WardenDashboardFragment)
//            loadingDialog.create()
//            loadingDialog.show()

            var noticesCount = NoticeAccess(
                profileViewModel,
                requireContext()
            ).getNoticesCount()

            binding.totalNoticesTextInWardenDashboard.text = noticesCount.toString()

//            loadingDialog.cancel()
            noticesCountCoroutineScope.cancel()
        }
    }
    private fun getStudentsCount() {
        val studentsCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        studentsCountCoroutineScope.launch {
            var loadingDialog = getLoadingDialog(requireContext(),this@WardenDashboardFragment)
//            loadingDialog.create()
//            loadingDialog.show()

            var studentsCount = HostelDataAccess(
                requireContext(),
                this@WardenDashboardFragment,
                profileViewModel.loginToken.toString()
            ).getHostelOccupantsCount(profileViewModel.currentWarden.hostelID.toString())

//            loadingDialog.cancel()
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
            viewsViewModel.updateLoadingState(true)

            val warden = ProfileAccess(requireContext(),profileViewModel).getWardenProfile()

            getProfileCoroutineScope.cancel()

            if(warden != null){
                binding.nameTextInWardenDashboard.text = warden.name.toString()
                binding.emailTextInWardenDashboard.text = warden.email.toString()
                profileViewModel.currentWarden = warden
                profileViewModel.username = warden.email.toString()
                getHostelDetails()
                getStudentsCount()
                getNoticesCount()
                getPendingComplaintsCount(loadingDialog)
            }else{
                loadingDialog.cancel()
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }

    private fun getHostelDetails() {
        val getHostelCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@WardenDashboardFragment)
        loadingDialog.create()
        getHostelCoroutineScope.launch {
//            loadingDialog.show()
            val hostel = HostelDataAccess(
                requireContext(),
                this@WardenDashboardFragment,
                profileViewModel.loginToken.toString()
            ).getHostelDetails(profileViewModel.currentWarden.hostelID.toString())
//            loadingDialog.cancel()
            getHostelCoroutineScope.cancel()
            if(hostel != null){
                binding.hostelNameTextInWardenDashboard.text = hostel.hostelID.toString()
                binding.totalRoomsTextInWardenDashboard.text = hostel.capacity.toString()
            }
        }
    }

}