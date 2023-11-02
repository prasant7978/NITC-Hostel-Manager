package com.kumar.nitchostelmanager.bills.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.databinding.OccupantsBillCardBinding
import com.kumar.nitchostelmanager.models.Bill

class AllBillsAdapter(private var bills: ArrayList<Bill>): RecyclerView.Adapter<AllBillsAdapter.BillViewHolder>(){
    class BillViewHolder(val binding: OccupantsBillCardBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val binding = OccupantsBillCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bills.size
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.binding.dueMonthInOccupantsBillCard.text = bills[position].billMonth
        holder.binding.dueYearInOccupantsBillCard.text = bills[position].billYear
        holder.binding.amountInOccupantsBillCard.text = bills[position].amount.toString()
        holder.binding.studentRollInOccupantsBillCard.text = bills[position].studentRoll.toString().uppercase()
        holder.binding.billTypeInOccupantsBillCard.text = bills[position].billType

        if(bills[position].paid.compareTo(1) == 0){
            holder.binding.paymentIdInOccupantsBillCard.visibility = View.VISIBLE
            holder.binding.dateInOccupantsBillCard.visibility = View.VISIBLE
            holder.binding.paymentDoneTextInOccupantsBillCard.visibility = View.VISIBLE
            holder.binding.statusInOccupantsBillCard.text = "paid"
            holder.binding.paymentIdInOccupantsBillCard.text = "Payment Id: " + bills[position].paymentID.toString()
            holder.binding.dateInOccupantsBillCard.text = bills[position].paymentDate
        }
        else{
            holder.binding.paymentDoneTextInOccupantsBillCard.visibility = View.GONE
            holder.binding.paymentIdInOccupantsBillCard.visibility = View.GONE
            holder.binding.dateInOccupantsBillCard.visibility = View.GONE
            holder.binding.statusInOccupantsBillCard.text = "pending"
        }

    }
}