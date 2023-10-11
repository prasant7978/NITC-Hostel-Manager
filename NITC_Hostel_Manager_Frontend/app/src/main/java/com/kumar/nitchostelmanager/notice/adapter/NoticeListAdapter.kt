package com.kumar.nitchostelmanager.notice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.databinding.NoticeCardBinding
import com.kumar.nitchostelmanager.models.Notice

class NoticeListAdapter(private var noticeList: ArrayList<Notice>): RecyclerView.Adapter<NoticeListAdapter.NoticeViewHolder>() {
    class NoticeViewHolder(val adapterBinding: NoticeCardBinding): RecyclerView.ViewHolder(adapterBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val binding = NoticeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.adapterBinding.noticeTitle.text = noticeList[position].heading
        holder.adapterBinding.noticeBody.text = noticeList[position].message
        holder.adapterBinding.date.text = noticeList[position].date


    }
}