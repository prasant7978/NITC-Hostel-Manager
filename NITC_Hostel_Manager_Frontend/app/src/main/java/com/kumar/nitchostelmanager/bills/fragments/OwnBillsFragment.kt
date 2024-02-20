package com.kumar.nitchostelmanager.bills.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.bills.access.BillAccess
import com.kumar.nitchostelmanager.bills.adapter.OwnBillsAdapter
import com.kumar.nitchostelmanager.databinding.FragmentOwnBillsBinding
import com.kumar.nitchostelmanager.models.Bill
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.ViewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OwnBillsFragment:Fragment(), CircleLoadingDialog {
    lateinit var binding: FragmentOwnBillsBinding
    val profileViewModel: ProfileViewModel by activityViewModels()
    private val viewsViewModel: ViewsViewModel by activityViewModels()
    private lateinit var ownBillsAdapter: OwnBillsAdapter
    private var billList: ArrayList<Bill>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_own_bills, container, false)

        binding.lifecycleOwner = this
        binding.mainViewModel = viewsViewModel
        viewsViewModel.updateLoadingState(true)

        getAllOwnBills()

        return binding.root
    }

    private fun getAllOwnBills(){
        val billCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(), this)

        billCoroutineScope.launch {
            viewsViewModel.updateLoadingState(true)
            loadingDialog.create()
            loadingDialog.show()

            billList = BillAccess(requireContext(), profileViewModel).getUnpaidBills()

            billCoroutineScope.cancel()
            viewsViewModel.updateLoadingState(false)
            loadingDialog.cancel()

            if(!billList.isNullOrEmpty()){
                billList!!.reverse()
                binding.recyclerViewInOwnBillsFragment.layoutManager = LinearLayoutManager(activity)
                ownBillsAdapter = OwnBillsAdapter(billList!!,requireContext(),profileViewModel)
                binding.recyclerViewInOwnBillsFragment.adapter = ownBillsAdapter
            }
            else{
                binding.recyclerViewInOwnBillsFragment.visibility = View.INVISIBLE
                Toast.makeText(context, "No bills are available", Toast.LENGTH_LONG).show()
            }
        }

    }
}