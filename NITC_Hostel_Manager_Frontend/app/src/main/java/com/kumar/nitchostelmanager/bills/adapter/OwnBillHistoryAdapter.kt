package com.kumar.nitchostelmanager.bills.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.databinding.BillHistoryCardBinding
import com.kumar.nitchostelmanager.models.Bill

class OwnBillHistoryAdapter(private var billHistory: ArrayList<Bill>): RecyclerView.Adapter<OwnBillHistoryAdapter.BillHistoryViewHolder>(){
    class BillHistoryViewHolder(val binding: BillHistoryCardBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillHistoryViewHolder {
        val binding = BillHistoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return billHistory.size
    }

    override fun onBindViewHolder(holder: BillHistoryViewHolder, position: Int) {
        holder.binding.billTypeInOwnBillCard.text = billHistory[position].billType
        holder.binding.dueMonthInOwnBillCard.text = billHistory[position].billMonth
        holder.binding.dueYearInOwnBillCard.text = billHistory[position].billYear
        holder.binding.amountInOwnBillCard.text = billHistory[position].amount.toString()
        holder.binding.paymentIdInOwnBillCard.text = billHistory[position].paymentID.toString()
        holder.binding.dateInOwnBillCard.text = billHistory[position].paymentDate
    }
}