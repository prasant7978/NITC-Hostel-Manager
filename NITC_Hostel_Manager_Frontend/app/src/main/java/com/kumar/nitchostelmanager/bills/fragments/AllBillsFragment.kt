package com.kumar.nitchostelmanager.bills.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.bills.access.BillAccess
import com.kumar.nitchostelmanager.bills.adapter.AllBillsAdapter
import com.kumar.nitchostelmanager.databinding.FragmentAllBillsBinding
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AllBillsFragment:Fragment(),CircleLoadingDialog {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()

    private lateinit var binding:FragmentAllBillsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBillsBinding.inflate(inflater,container,false)

        getAllBills()

        return binding.root
    }

    private fun getAllBills() {
        val getBillsCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AllBillsFragment)
        getBillsCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val bills = BillAccess(
                requireContext(),
                profileViewModel
            ).getAllBills()
            loadingDialog.cancel()
            getBillsCoroutineScope.cancel()
            if(bills!= null){
                binding.recyclerViewInAllBillsFragment.layoutManager = LinearLayoutManager(context)
                binding.recyclerViewInAllBillsFragment.adapter = AllBillsAdapter(
                    bills
                )
            }
        }
    }

}