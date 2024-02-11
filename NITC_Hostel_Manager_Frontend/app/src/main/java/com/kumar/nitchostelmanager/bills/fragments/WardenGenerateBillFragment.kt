package com.kumar.nitchostelmanager.bills.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.Validation
import com.kumar.nitchostelmanager.bills.access.BillAccess
import com.kumar.nitchostelmanager.databinding.FragmentWardenGenerateBillBinding
import com.kumar.nitchostelmanager.models.Bill
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class WardenGenerateBillFragment : Fragment(), AdapterView.OnItemSelectedListener, Validation {
    lateinit var binding: FragmentWardenGenerateBillBinding
    val profileViewModel: ProfileViewModel by activityViewModels()
    private var billMonth = ""
    private var billYear = ""
    private var months: Array<String>? = null
    private var years: Array<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWardenGenerateBillBinding.inflate(inflater, container, false)

        setSpinner()

        binding.generateBillButtonInGenerateBill.setOnClickListener {
            val billType = binding.billTypeInputInGenerateBill.text.toString()
            val amount = binding.amountInputInGenerateBill.text.toString()

            if(billType.isEmpty()){
                binding.billTypeInputInGenerateBill.error = "Enter The Bill Type"
                binding.billTypeInputInGenerateBill.requestFocus()
                return@setOnClickListener
            }

            if(amount.isEmpty()){
                binding.amountInputInGenerateBill.error = "Enter The Amount"
                binding.amountInputInGenerateBill.requestFocus()
                return@setOnClickListener
            }
            else if(!checkValidAmount(amount)){
                binding.amountInputInGenerateBill.error = "Enter Valid Amount"
                binding.amountInputInGenerateBill.requestFocus()
                return@setOnClickListener
            }

            showAlertDialog(billType, amount)
        }

//        val backCallback = object: OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                findNavController().navigate(R.id.allBillsFragment)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)

        return binding.root
    }

    private fun setSpinner() {
        val simpleDate = SimpleDateFormat("dd/M/yyyy")
        val currentDate = simpleDate.format(Date())
        val date: List<String> = currentDate.split("/")

        val currentYear = date[2].toInt()
        val prevYear = currentYear - 1
        val nextYear = currentYear + 1

        months = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        years = arrayOf(prevYear.toString(), currentYear.toString(), nextYear.toString())
//        binding.monthSpinnerInGenerateBill
        binding.monthSpinnerInGenerateBill.onItemSelectedListener = this@WardenGenerateBillFragment
        binding.monthSpinnerInGenerateBill.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,
            months!!
        )

        binding.yearSpinnerInGenerateBill.onItemSelectedListener = this@WardenGenerateBillFragment
        binding.yearSpinnerInGenerateBill.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,
            years!!
        )
    }

    private fun showAlertDialog(billType: String, amount: String){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Generate Bill")
        dialog?.setMessage("Are you sure you want to generate bill for $billMonth $billYear")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            generateBill(billType, amount)
        })
        dialog?.create()?.show()
    }

    private fun generateBill(billType: String, amount: String) {
        val bill = Bill(
            amount = amount.toDouble(),
            billMonth = billMonth,
            billYear = billYear,
            billType = billType,
            paid = 0,
            hostelID = profileViewModel.currentWarden.hostelID
        )

        val billCoroutineScope = CoroutineScope(Dispatchers.Main)
        billCoroutineScope.launch {
            val generateBill = BillAccess(requireContext(), profileViewModel).generateBill(bill)
            billCoroutineScope.cancel()

            if(generateBill){
                Snackbar.make(
                    binding.constraintLayoutInGenerateBillFragment,
                    "Bill has been generated for $billMonth $billYear",
                    Snackbar.LENGTH_SHORT
                ).setAction("Close", View.OnClickListener { }).show()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(parent!!.id == R.id.monthSpinnerInGenerateBill){
            billMonth = months?.get(position).toString()
        }else{
            billYear = years?.get(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}