package com.kumar.nitchostelmanager.complaints.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.complaints.access.ComplaintsDataAccess
import com.kumar.nitchostelmanager.complaints.adapters.OwnComplaintsAdapter
import com.kumar.nitchostelmanager.databinding.FragmentViewOwnComplaintsBinding
import com.kumar.nitchostelmanager.models.Complaint
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ViewOwnComplaintsFragment : Fragment() {
    private lateinit var binding: FragmentViewOwnComplaintsBinding
    private lateinit var complaintsListAdapter: OwnComplaintsAdapter
    private var complaintsList: Array<Complaint>? = null
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewOwnComplaintsBinding.inflate(inflater, container, false)

        getOwnComplaints()

        binding.addComplaintInViewOwnComplaints.setOnClickListener {
            Log.d("click", "click")
            findNavController().navigate(R.id.addComplaintFragment)
        }

        return binding.root
    }

    private fun getOwnComplaints(){
        val complaintCoroutineScope = CoroutineScope(Dispatchers.Main)
        complaintCoroutineScope.launch {
            complaintsList = ComplaintsDataAccess(
                requireContext(),
                this@ViewOwnComplaintsFragment,
                profileViewModel.loginToken.toString()
            ).viewOwnComplaint()
            complaintCoroutineScope.cancel()

            if(!complaintsList.isNullOrEmpty()){
                complaintsList!!.reverse()
                binding.recyclerviewInViewOwnComplaints.layoutManager = LinearLayoutManager(activity)
                complaintsListAdapter = OwnComplaintsAdapter(complaintsList!!, requireContext())
                binding.recyclerviewInViewOwnComplaints.adapter = complaintsListAdapter
            }
            else {
                binding.recyclerviewInViewOwnComplaints.visibility = View.INVISIBLE
                Toast.makeText(context, "No complaints are available", Toast.LENGTH_LONG).show()
            }
        }
    }

}