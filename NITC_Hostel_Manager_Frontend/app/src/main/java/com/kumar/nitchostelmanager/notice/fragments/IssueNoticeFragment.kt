package com.kumar.nitchostelmanager.notice.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentIssueNoticeBinding
import com.kumar.nitchostelmanager.models.Notice
import com.kumar.nitchostelmanager.notice.access.NoticeAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class IssueNoticeFragment : Fragment(),CircleLoadingDialog {
    private lateinit var binding: FragmentIssueNoticeBinding
    private var referTo: String = ""
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIssueNoticeBinding.inflate(inflater, container, false)

        binding.publishNoticeButtonInIssueNoticeFragment.setOnClickListener {
            val headingText = binding.headingInputInIssueNoticeFragment.text.toString()
            if(headingText.isEmpty()){
                binding.headingInputInIssueNoticeFragment.error = "Enter heading"
                binding.headingInputInIssueNoticeFragment.requestFocus()
                return@setOnClickListener
            }

            val messageText = binding.editTextNoticeMessage.text.toString()
            if(messageText.isEmpty()){
                binding.editTextNoticeMessage.error = "Enter heading"
                binding.editTextNoticeMessage.requestFocus()
                return@setOnClickListener
            }

            AlertDialog.Builder(context)
                .setTitle("Publish Notice")
                .setMessage("Are you sure you want to publish this notice?")
                .setCancelable(false)
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, which ->
                    dialogInterface.cancel()
                })
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, which ->
                    publishNotice(headingText,messageText)
                }).create().show()

        }

//        val backCallback = object: OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                findNavController().navigate(R.id.noticeListFragment)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)

        return binding.root
    }

    private fun publishNotice(headingText:String,messageText:String){
        binding.progressBar.visibility = View.VISIBLE
        binding.publishNoticeButtonInIssueNoticeFragment.isClickable = false

        val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = simpleDate.format(Date())

        val dateAndTime: List<String> = currentDate.split(" ")
        var hostelID:String? = null
        if(profileViewModel.userType == "Warden") hostelID = profileViewModel.currentWarden.hostelID.toString()

        val notice = Notice(
            date = dateAndTime[0],
            heading = headingText,
            messageText,
            hostelID,
            profileViewModel.username.toString()
        )

        val publishNoticeCoroutineScope = CoroutineScope(Dispatchers.Main)
        publishNoticeCoroutineScope.launch {
            val createNotice = NoticeAccess(profileViewModel, requireContext()).issueNotice(notice)
            publishNoticeCoroutineScope.cancel()

            if(createNotice) {
                Snackbar.make(
                    binding.issueNoticeOuterLayout,
                    "Notice has been published successfully",
                    Snackbar.LENGTH_LONG
                ).setAction("Close", View.OnClickListener { }).show()
                clearAllTextArea()
            }
        }

        binding.progressBar.visibility = View.INVISIBLE
        binding.publishNoticeButtonInIssueNoticeFragment.isClickable = true
    }

    private fun clearAllTextArea(){
        binding.headingInputInIssueNoticeFragment.setText("")
        binding.editTextNoticeMessage.setText("")
    }
}