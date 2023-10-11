package com.kumar.nitchostelmanager.payments.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.payments.access.PaymentAccess
import com.kumar.nitchostelmanager.profile.access.ProfileAccess
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentPaymentBinding
import com.kumar.nitchostelmanager.models.Payment
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class StudentPaymentFragment : Fragment() {
    private lateinit var paymentBinding: FragmentPaymentBinding
    private var totalDue: Double = 0.0
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        paymentBinding = FragmentPaymentBinding.inflate(inflater, container, false)

        showTotalDues()

        paymentBinding.buttonPayBill.setOnClickListener {
            showDialog()
        }

        return paymentBinding.root
    }

    private fun showTotalDues(){
        val profileCoroutineScope = CoroutineScope(Dispatchers.Main)
        profileCoroutineScope.launch {
            totalDue = ProfileAccess(requireContext(), profileViewModel).getDue()
            profileCoroutineScope.cancel()

            paymentBinding.textViewTotalDue.text = totalDue.toString()
        }
    }

    private fun showDialog(){
        paymentBinding.buttonPayBill.isClickable = false
        paymentBinding.progressBar.visibility = View.VISIBLE

        if(totalDue != 0.0){
            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Pay Hostel Bill")
            dialog.setCancelable(false)
            dialog.setMessage("A amount of " + totalDue + " will be credited from your bank account")
            dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                issueBill()
            })
            dialog.create().show()
        }
        else{
            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Pay Hostel Bill")
            dialog.setCancelable(false)
            dialog.setMessage("You don't have any due to pay")
            dialog.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            dialog.create().show()
        }

        paymentBinding.progressBar.visibility = View.INVISIBLE
        paymentBinding.buttonPayBill.isClickable = true
    }

    private fun issueBill(){
        val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = simpleDate.format(Date())

        val dateAndTime: List<String> = currentDate.split(" ")

        val payment = Payment(
            studentRoll = profileViewModel.currentStudent.studentRoll,
            status = "success",
            date = dateAndTime[0],
            time = dateAndTime[1],
            amount = totalDue
        )

        val issueBillCoroutineScope = CoroutineScope(Dispatchers.Main)
        issueBillCoroutineScope.launch {
            val payBill: Boolean = PaymentAccess(requireContext(), profileViewModel).payDues(payment)
            issueBillCoroutineScope.cancel()

            if(payBill){
                Snackbar.make(paymentBinding.constraintLayoutStudentPayment,"Your payment was successful", Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()

                this@StudentPaymentFragment.findNavController().navigate(R.id.studentDashboardFragment)
            }
        }
    }
}