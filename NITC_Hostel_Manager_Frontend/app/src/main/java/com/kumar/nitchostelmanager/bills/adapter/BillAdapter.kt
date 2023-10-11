package com.kumar.nitchostelmanager.bills.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.databinding.BillCardBinding
import com.kumar.nitchostelmanager.models.Bill

class BillAdapter(private var billList: ArrayList<Bill>): RecyclerView.Adapter<BillAdapter.BillViewHolder>(){
    class BillViewHolder(val adapterBinding: BillCardBinding): RecyclerView.ViewHolder(adapterBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val binding = BillCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return billList.size
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.adapterBinding.dueMonthInBillCard.text = billList[position].billMonth
        holder.adapterBinding.dueYearInBillCard.text = billList[position].billYear
        holder.adapterBinding.paymentIdInBillCard.text = billList[position].paymentID.toString()
        holder.adapterBinding.amountInBillCard.text = billList[position].amount.toString()
        holder.adapterBinding.dateInBillCard.text = billList[position].paymentDate
    }
}