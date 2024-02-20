package com.kumar.nitchostelmanager.bills.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.bills.access.BillAccess
import com.kumar.nitchostelmanager.bills.adapter.AllBillsAdapter
import com.kumar.nitchostelmanager.databinding.FragmentAllBillsBinding
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import com.kumar.nitchostelmanager.viewModel.ViewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AllBillsFragment:Fragment(),CircleLoadingDialog {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private val viewsViewModel: ViewsViewModel by activityViewModels()

    private lateinit var binding:FragmentAllBillsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_bills, container,false)

        binding.lifecycleOwner = this
        binding.mainViewModel = viewsViewModel
        viewsViewModel.updateLoadingState(true)

        getAllBills()

        if(profileViewModel.userType == "Admin")
            binding.generateBillButtonInAllBillsFragment.visibility = View.GONE

        binding.generateBillButtonInAllBillsFragment.setOnClickListener {
            findNavController().navigate(R.id.wardenGenerateBillFragment)
        }

        return binding.root
    }

    private fun getAllBills() {
        val getBillsCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AllBillsFragment)

        getBillsCoroutineScope.launch {
            viewsViewModel.updateLoadingState(true)
            loadingDialog.create()
            loadingDialog.show()

            val bills = BillAccess(requireContext(), profileViewModel).getAllBills()

            loadingDialog.cancel()
            viewsViewModel.updateLoadingState(false)
            getBillsCoroutineScope.cancel()

            if(bills!= null){
                Log.d("bills", bills.toString())
                binding.recyclerViewInAllBillsFragment.layoutManager = LinearLayoutManager(context)
                binding.recyclerViewInAllBillsFragment.adapter = AllBillsAdapter(bills)
            }
        }
    }

}