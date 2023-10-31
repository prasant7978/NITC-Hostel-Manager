package com.kumar.nitchostelmanager.hostels.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.databinding.HostelCardStudentBinding
import com.kumar.nitchostelmanager.models.Hostel

class HostelListStudentAdapter(var hostelList: ArrayList<Hostel>): RecyclerView.Adapter<HostelListStudentAdapter.HostelListViewHolder>() {
    class HostelListViewHolder(val binding: HostelCardStudentBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HostelListViewHolder {
        val binding = HostelCardStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HostelListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hostelList.size
    }

    override fun onBindViewHolder(holder: HostelListViewHolder, position: Int) {
        holder.binding.hostelNameInHostelCardStudent.text = hostelList[position].hostelID
        holder.binding.inmatesTypeInHostelCardStudent.text = hostelList[position].occupantsGender
        holder.binding.chargesInHostelCardStudent.text = hostelList[position].charges.toString()
        holder.binding.wardenEmailInHostelCardStudent.text = hostelList[position].wardenEmail
        holder.binding.capacityInHostelCardStudent.text = hostelList[position].capacity.toString()
    }
}