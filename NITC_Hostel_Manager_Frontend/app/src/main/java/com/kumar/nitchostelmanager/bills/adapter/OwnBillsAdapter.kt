package com.kumar.nitchostelmanager.bills.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.databinding.PendingBillCardBinding
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
    class BillViewHolder(val binding: PendingBillCardBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val binding = PendingBillCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return billList.size
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.binding.dueMonthInPendingBillCard.text = billList[position].billMonth
        holder.binding.dueYearInPendingBillCard.text = billList[position].billYear
        holder.binding.billTypeInPendingBillCard.text = billList[position].billType.toString()
        holder.binding.amountInPendingBillCard.text = billList[position].amount.toString()

        holder.binding.billCardInPendingBillCard.setOnClickListener {
            if(billList[position].paid.compareTo(1) != 0)
                payBill(position, billList[position].amount)
        }
    }

    private fun payBill(position: Int, amount: Double) {

        AlertDialog.Builder(context)
            .setTitle("Pay this bill")
            .setMessage("A amount of " + amount + " will be debited from your bank account")
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
                        billList.removeAt(position)
                        notifyDataSetChanged()
                        Toast.makeText(context,"Amount paid successfully",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("No"){dialog,which->
                dialog.cancel()
            }
            .create().show()

    }

}