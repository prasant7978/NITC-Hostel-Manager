package com.kumar.nitchostelmanager.complaints.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.ComplaintOwnCardBinding
import com.kumar.nitchostelmanager.models.Complaint

class OwnComplaintsAdapter(
    private val complaintsList: Array<Complaint>,
    val context: Context
): RecyclerView.Adapter<OwnComplaintsAdapter.ComplaintsViewHolder>() {
    class ComplaintsViewHolder(val adapterBinding: ComplaintOwnCardBinding): RecyclerView.ViewHolder(adapterBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintsViewHolder {
        val binding = ComplaintOwnCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComplaintsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return complaintsList.size
    }

    override fun onBindViewHolder(holder: ComplaintsViewHolder, position: Int) {
        val status = complaintsList[position].status
        holder.adapterBinding.complaintStatusInComplaintCard.text = status

        if(status == "pending")
            holder.adapterBinding.complaintStatusInComplaintCard.setTextColor(ContextCompat.getColor(context, R.color.light_orange))
        else if(status == "completed")
            holder.adapterBinding.complaintStatusInComplaintCard.setTextColor(ContextCompat.getColor(context, R.color.light_green))
        else if(status == "rejected")
            holder.adapterBinding.complaintStatusInComplaintCard.setTextColor(ContextCompat.getColor(context, R.color.light_red))

        holder.adapterBinding.date.text = complaintsList[position].date
        holder.adapterBinding.complaintMessageInComplaintCard.text = complaintsList[position].message
    }
}