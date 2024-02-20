package com.kumar.nitchostelmanager.notice.fragments

import android.content.DialogInterface
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentNoticeListBinding
import com.kumar.nitchostelmanager.models.Notice
import com.kumar.nitchostelmanager.notice.access.NoticeAccess
import com.kumar.nitchostelmanager.notice.adapter.NoticeListAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.ViewsViewModel
//import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NoticeListFragment : Fragment(), CircleLoadingDialog {
    private lateinit var binding: FragmentNoticeListBinding
    private lateinit var noticeListAdapter: NoticeListAdapter
    val profileViewModel: ProfileViewModel by activityViewModels()
    private val viewsViewModel: ViewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notice_list, container, false)

        binding.lifecycleOwner = this
        binding.mainViewModel = viewsViewModel
        viewsViewModel.updateLoadingState(true)

        if(profileViewModel.userType == "Student")
            binding.addNoticeInNoticeListFragment.visibility = View.INVISIBLE

        getAllNotices()

        if(profileViewModel.userType == "Warden" || profileViewModel.userType == "Admin"){
            binding.addNoticeInNoticeListFragment.setOnClickListener {
                findNavController().navigate(R.id.issueNoticeFragment)
            }
        }

        return binding.root
    }

    private fun getAllNotices(){
        val getNoticeCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(), this@NoticeListFragment)

        getNoticeCoroutineScope.launch {
            viewsViewModel.updateLoadingState(true)
            loadingDialog.create()
            loadingDialog.show()

            val noticeList: ArrayList<Notice>? = NoticeAccess(profileViewModel, requireContext()).getAllNotices()

            loadingDialog.cancel()
            getNoticeCoroutineScope.cancel()
            viewsViewModel.updateLoadingState(false)

            if(!noticeList.isNullOrEmpty()){
                noticeList.reverse()
                binding.recyclerViewInNoticeListFragment.layoutManager = LinearLayoutManager(activity)
                noticeListAdapter = NoticeListAdapter(noticeList)
                binding.recyclerViewInNoticeListFragment.adapter = noticeListAdapter
            }
        }

    }

}