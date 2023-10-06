package com.kumar.nitchostelmanager.notice.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
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

class IssueNoticeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var issueNoticeBinding: FragmentIssueNoticeBinding
    private var referTo: String = ""
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        issueNoticeBinding = FragmentIssueNoticeBinding.inflate(inflater, container, false)

        issueNoticeBinding.referTo.onItemSelectedListener = this
        val referToAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.referTo, android.R.layout.simple_spinner_item)
        referToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        issueNoticeBinding.referTo.adapter = referToAdapter

        issueNoticeBinding.buttonPublishNotice.setOnClickListener {
            showDialog()
        }

        return issueNoticeBinding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, index: Int, id: Long) {
        if(parent != null)
            referTo = parent.getItemAtPosition(index).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun showDialog(){
        var dialog = AlertDialog.Builder(activity)
        dialog.setTitle("Publish Notice")
            .setMessage("Are you sure you want to publish this notice?")
            .setCancelable(false)
            .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, which ->
                dialogInterface.cancel()
            })
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, which ->
                publishNotice()
            })
        dialog.create().show()
    }

    private fun publishNotice(){
        issueNoticeBinding.progressBar.visibility = View.VISIBLE
        issueNoticeBinding.buttonPublishNotice.isClickable = false

        val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = simpleDate.format(Date())

        val dateAndTime: List<String> = currentDate.split(" ")

        val notice = Notice(
            date = dateAndTime[0],
            title = issueNoticeBinding.editTextNoticeTitle.text.toString(),
            message = issueNoticeBinding.editTextNoticeMessage.text.toString()
        )

        val publishNoticeCoroutineScope = CoroutineScope(Dispatchers.Main)
        publishNoticeCoroutineScope.launch {
            val createNotice = NoticeAccess(profileViewModel, requireContext()).issueNotice(notice)
            publishNoticeCoroutineScope.cancel()

            if(createNotice) {
                Snackbar.make(
                    issueNoticeBinding.issueNoticeOuterLayout,
                    "Notice has been published successfully",
                    Snackbar.LENGTH_LONG
                ).setAction("Close", View.OnClickListener { }).show()
                clearAllTextArea()
            }
        }

        issueNoticeBinding.progressBar.visibility = View.INVISIBLE
        issueNoticeBinding.buttonPublishNotice.isClickable = true
    }

    private fun clearAllTextArea(){
        issueNoticeBinding.editTextNoticeTitle.setText("")
        issueNoticeBinding.editTextNoticeMessage.setText("")
    }
}