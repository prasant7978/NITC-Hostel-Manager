package com.kumar.nitchostelmanager.bills.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.OwnBillCardBinding
import com.kumar.nitchostelmanager.models.Bill
import com.kumar.nitchostelmanager.models.Payment
import com.kumar.nitchostelmanager.payments.access.PaymentAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class OwnBillsAdapter(
    private var billList: ArrayList<Bill>,
    val context:Context,
    var profileViewModel: ProfileViewModel
): RecyclerView.Adapter<OwnBillsAdapter.BillViewHolder>(){
    class BillViewHolder(val binding: OwnBillCardBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val binding = OwnBillCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return billList.size
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.binding.dueMonthInOwnBillCard.text = billList[position].billMonth
        holder.binding.dueYearInOwnBillCard.text = billList[position].billYear
        holder.binding.billTypeInOwnBillCard.text = billList[position].billType.toString()
        holder.binding.amountInOwnBillCard.text = billList[position].amount.toString()
        if(billList[position].paid.compareTo(1) == 0){
            holder.binding.paymentIdInOwnBillCard.visibility = View.VISIBLE
            holder.binding.paymentDateTVInOwnBillCard.visibility = View.VISIBLE
            holder.binding.dateInOwnBillCard.text = billList[position].paymentDate
            holder.binding.paymentIdInOwnBillCard.text = billList[position].paymentID.toString()
        }else{
            holder.binding.paymentIdInOwnBillCard.visibility = View.GONE
            holder.binding.paymentDateTVInOwnBillCard.visibility = View.GONE
        }
        holder.binding.billCardInOwnBillCard.setOnClickListener {
            if(billList[position].paid.compareTo(1) != 0) payBill(position)
        }
    }

    private fun payBill(position: Int) {

        AlertDialog.Builder(context)
            .setTitle("Pay this bill")
            .setPositiveButton("Yes"){dialog,which->

                val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = simpleDate.format(Date())

                val dateAndTime: List<String> = currentDate.split(" ")
                val payment = Payment(
                    studentRoll = profileViewModel.currentStudent.studentRoll,
                    status = "success",
                    date = dateAndTime[0],
                    time = dateAndTime[1],
                    amount = billList[position].amount,
                    billID = billList[position].billID
                )

                val issueBillCoroutineScope = CoroutineScope(Dispatchers.Main)
                issueBillCoroutineScope.launch {
                    val payBill: Boolean = PaymentAccess(context, profileViewModel).payDues(payment)
                    issueBillCoroutineScope.cancel()

                    if(payBill){
                        Toast.makeText(context,"Paid",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("No"){dialog,which->
                dialog.cancel()
            }
            .create().show()

    }
}