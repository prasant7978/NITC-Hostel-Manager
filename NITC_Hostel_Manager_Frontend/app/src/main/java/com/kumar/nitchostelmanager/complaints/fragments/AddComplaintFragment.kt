package com.kumar.nitchostelmanager.complaints.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.complaints.access.ManageComplaintAccess
import com.kumar.nitchostelmanager.databinding.FragmentAddComplaintBinding
import com.kumar.nitchostelmanager.models.Complaint
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class AddComplaintFragment : Fragment(), CircleLoadingDialog {
    private lateinit var binding: FragmentAddComplaintBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddComplaintBinding.inflate(inflater, container, false)

        binding.buttonPostComplaint.setOnClickListener {
            val message = binding.complaintMessage.text.toString()
            if(message.isEmpty() || message.isBlank())
                Snackbar.make(
                    binding.postComplaintLayout,
                    "Please enter some text or content before posting",
                    Snackbar.LENGTH_LONG
                ).setAction("Close", View.OnClickListener { }).show()
            else {
                val loadingDialog = getLoadingDialog(requireContext(), this@AddComplaintFragment)
                loadingDialog.create()
                loadingDialog.show()
                binding.buttonPostComplaint.isClickable = false

                postComplaint(message)

                binding.buttonPostComplaint.isClickable = true
                loadingDialog.cancel()
            }
        }

        return binding.root
    }

    private fun postComplaint(complaintMessage: String){
        val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = simpleDate.format(Date())

        val dateAndTime: List<String> = currentDate.split(" ")

        val complaint = Complaint(
            status = "pending",
            studentRoll = profileViewModel.currentStudent.studentRoll,
            message = complaintMessage,
            date = dateAndTime[0],
            time = dateAndTime[1],
            roomNumber= profileViewModel.currentStudent.roomNumber,
            hostelID = profileViewModel.currentStudent.hostelID.toString()
        )

        val complaintCoroutineScope = CoroutineScope(Dispatchers.Main)
        complaintCoroutineScope.launch {
            val issuedComplaint = ManageComplaintAccess(requireContext(), profileViewModel).issueComplaint(complaint)
            complaintCoroutineScope.cancel()

            if(issuedComplaint) {
                Snackbar.make(
                    binding.postComplaintLayout,
                    "Your complaint has been issued successfully",
                    Snackbar.LENGTH_LONG
                ).setAction("Close", View.OnClickListener { }).show()
                binding.complaintMessage.setText("")
            }
        }
    }

}