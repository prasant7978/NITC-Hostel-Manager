package com.kumar.nitchostelmanager.notice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentNoticeListBinding

class NoticeListFragment : Fragment() {
    lateinit var noticeListBinding: FragmentNoticeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noticeListBinding = FragmentNoticeListBinding.inflate(inflater, container, false)



        return noticeListBinding.root
    }

}