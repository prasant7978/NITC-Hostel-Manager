package com.kumar.nitchostelmanager.complaints.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.ComplaintAllCardBinding
import com.kumar.nitchostelmanager.models.Complaint
import java.util.ArrayList

class AllComplaintsAdapter(
    private val complaintList: ArrayList<Complaint>,
    val context: Context
): RecyclerView.Adapter<AllComplaintsAdapter.AllComplaintsViewHolder>(){
    class AllComplaintsViewHolder(val adapterBinding: ComplaintAllCardBinding): RecyclerView.ViewHolder(adapterBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllComplaintsViewHolder {
        val binding = ComplaintAllCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllComplaintsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return complaintList.size
    }

    override fun onBindViewHolder(holder: AllComplaintsViewHolder, position: Int) {
        holder.adapterBinding.studentRollInAllComplaintCard.text = complaintList[position].studentRoll
        holder.adapterBinding.roomNoInAllComplaintCard.text = complaintList[position].roomNumber.toString()
        holder.adapterBinding.messageInAllComplaintCard.text = complaintList[position].message

        val status = complaintList[position].status
        holder.adapterBinding.statusInAllComplaintCard.text = status

        if(status == "pending")
            holder.adapterBinding.statusInAllComplaintCard.setTextColor(ContextCompat.getColor(context, R.color.light_orange))
        else if(status == "completed")
            holder.adapterBinding.statusInAllComplaintCard.setTextColor(ContextCompat.getColor(context, R.color.light_green))
        else if(status == "rejected")
            holder.adapterBinding.statusInAllComplaintCard.setTextColor(ContextCompat.getColor(context, R.color.light_red))


    }
}