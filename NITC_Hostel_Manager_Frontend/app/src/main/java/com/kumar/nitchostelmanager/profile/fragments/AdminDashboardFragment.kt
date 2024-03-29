package com.kumar.nitchostelmanager.profile.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.LocalStorageAccess
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.bills.access.BillAccess
import com.kumar.nitchostelmanager.students.access.ManageStudentAccess
import com.kumar.nitchostelmanager.databinding.FragmentAdminDashboardBinding
import com.kumar.nitchostelmanager.hostels.access.ManageHostelsAccess
import com.kumar.nitchostelmanager.hostels.adapters.HostelListAdapter
import com.kumar.nitchostelmanager.notice.access.NoticeAccess
import com.kumar.nitchostelmanager.profile.access.ProfileAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import com.kumar.nitchostelmanager.viewModel.ViewsViewModel
import com.kumar.nitchostelmanager.wardens.access.WardensDataAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AdminDashboardFragment:Fragment(),CircleLoadingDialog {
    private lateinit var binding:FragmentAdminDashboardBinding
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val viewsViewModel:ViewsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_admin_dashboard,container,false)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewsViewModel
        viewsViewModel.updateLoadingState(true)
        getProfile()

        binding.showAllHostelsButtonInAdminDashboard.setOnClickListener {
            findNavController().navigate(R.id.allHostelsFragment)
        }

        binding.totalWardensCardInAdminDashboard.setOnClickListener {
            findNavController().navigate(R.id.wardenListFragment)
        }

        binding.totalStudentsCardInAdminDashboard.setOnClickListener {
            findNavController().navigate(R.id.allStudentsFragment)
        }

        binding.totalBillsCardInAdminDashboard.setOnClickListener {
            findNavController().navigate(R.id.allBillsFragment)
        }

        binding.noticesCardInAdminDashboard.setOnClickListener {
            findNavController().navigate(R.id.noticeListFragment)
        }

        binding.swipeRefreshLayoutInAdminDashboard.setOnRefreshListener {
            getProfile()
            binding.swipeRefreshLayoutInAdminDashboard.isRefreshing = false
        }

        binding.logoutButtonInAdminDashboard.setOnClickListener {
            showDialog()
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
                this@AdminDashboardFragment,
                requireContext(),
                profileViewModel
            ).deleteData()
            findNavController().navigate(R.id.loginFragment)
        })
        dialog?.create()?.show()
    }

    private fun getProfile() {
        val getProfileCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AdminDashboardFragment)

        getProfileCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            viewsViewModel.updateLoadingState(true)
            val admin = ProfileAccess(
                requireContext(),
                profileViewModel
            ).getAdminProfile()
            getProfileCoroutineScope.cancel()
            if(admin!= null){
                profileViewModel.username = admin.email.toString()
                profileViewModel.currentAdmin = admin
                getStudentsCount()
                getTotalBillsCount()
                getTotalNotices()
                getHostels()
                getWardensCount(loadingDialog)
            }else{
                Toast.makeText(context,"Error in logging you in. Login Again",Toast.LENGTH_SHORT).show()
                LocalStorageAccess(
                    this@AdminDashboardFragment,
                    requireContext(),
                    profileViewModel
                ).deleteData()
                loadingDialog.cancel()
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }

    private fun getWardensCount(loadingDialog: Dialog) {
        var getWardensCoroutineScope = CoroutineScope(Dispatchers.Main)
        getWardensCoroutineScope.launch {
            var wardensCount = WardensDataAccess(
                requireContext(),
                profileViewModel.loginToken.toString()
            ).getWardensCount(binding.parentLayoutInAdminDashboard)
            viewsViewModel.updateLoadingState(false)
            loadingDialog.cancel()
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
//                binding.addHostelsButtonInAdminDashboard.visibility = View.GONE
                binding.hostelsRecyclerViewInAdminDashboard.visibility = View.VISIBLE
                binding.hostelsRecyclerViewInAdminDashboard.layoutManager = LinearLayoutManager(context)
                binding.hostelsRecyclerViewInAdminDashboard.adapter = HostelListAdapter(
                    requireContext(),
                    profileViewModel.loginToken.toString(),
                    this@AdminDashboardFragment,
                    sharedViewModel,
                    hostels,
                    {hostelID->
                        sharedViewModel.updatingHostelID = hostelID
                        findNavController().navigate(R.id.addHostelFragment)
                    },
                    {hostelID,wardenEmail->
                        deleteHostel(hostelID,wardenEmail)
                    }
                )
            }else{
                binding.hostelsRecyclerViewInAdminDashboard.visibility = View.GONE
//                binding.addHostelsButtonInAdminDashboard.visibility = View.VISIBLE
                Toast.makeText(context,"No hostels till now",Toast.LENGTH_SHORT).show()
            }
            getwardenCoroutineScope.cancel()
        }
    }

    private fun deleteHostel(hostelID:String,wardenEmail:String?) {
        val deleteCoroutineScope = CoroutineScope(Dispatchers.Main)
        deleteCoroutineScope.launch {
            val deleted = ManageHostelsAccess(
                requireContext(),
                profileViewModel.loginToken.toString(),
                this@AdminDashboardFragment
            ).deleteHostel(hostelID,wardenEmail)
            deleteCoroutineScope.cancel()
            if(deleted){
                Toast.makeText(context,"Hostel deleted",Toast.LENGTH_SHORT).show()
                getHostels()
            }
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

    private fun getTotalBillsCount() {
        var totalBillsCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        totalBillsCountCoroutineScope.launch {
            var billsCount = BillAccess(
                requireContext(),
                profileViewModel
            ).getBillCount()

            binding.billsTextInAdminDashboard.text = billsCount.toString()
            totalBillsCountCoroutineScope.cancel()
        }
    }

    private fun getStudentsCount() {
        var studentsCountCoroutineScope = CoroutineScope(Dispatchers.Main)
        studentsCountCoroutineScope.launch {
            var studentsCount = ManageStudentAccess(requireContext(),
                this@AdminDashboardFragment,
                profileViewModel = profileViewModel
            ).getAllStudentsCount()
            if(studentsCount != null && studentsCount["BoysCount"] != null && studentsCount["GirlsCount"]!= null){
                binding.totalStudentsTextInAdminDashboard.text = ((studentsCount["BoysCount"]!! + studentsCount["GirlsCount"]!!)).toString()
            }else{
                Toast.makeText(requireContext(),"Error in getting students count", Toast.LENGTH_SHORT).show()
            }
            studentsCountCoroutineScope.cancel()
        }
    }
}