package com.kumar.nitchostelmanager.notice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentNoticeListBinding
import com.kumar.nitchostelmanager.models.Notice
import com.kumar.nitchostelmanager.notice.access.NoticeAccess
import com.kumar.nitchostelmanager.notice.adapter.NoticeListAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NoticeListFragment : Fragment() {
    private lateinit var noticeListBinding: FragmentNoticeListBinding
    private lateinit var noticeListAdapter: NoticeListAdapter
    val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noticeListBinding = FragmentNoticeListBinding.inflate(inflater, container, false)

        getAllNotices()

        return noticeListBinding.root
    }

    private fun getAllNotices(){
        val getNoticeCoroutineScope = CoroutineScope(Dispatchers.Main)
        getNoticeCoroutineScope.launch {
            val noticeList: ArrayList<Notice>? = NoticeAccess(profileViewModel, requireContext()).getAllNotices()
            getNoticeCoroutineScope.cancel()

            if(!noticeList.isNullOrEmpty()){
                noticeList.reverse()
                noticeListBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
                noticeListAdapter = NoticeListAdapter(noticeList)
                noticeListBinding.recyclerView.adapter = noticeListAdapter
            }
            else
                Toast.makeText(context, "No notices available", Toast.LENGTH_SHORT).show()
        }
    }
}