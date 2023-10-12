package com.kumar.nitchostelmanager.bills.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.bills.access.BillAccess
import com.kumar.nitchostelmanager.bills.adapter.OwnBillsAdapter
import com.kumar.nitchostelmanager.databinding.FragmentStudentPaymentHistoryBinding
import com.kumar.nitchostelmanager.models.Bill
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentPaymentHistoryFragment : Fragment() {
    lateinit var binding: FragmentStudentPaymentHistoryBinding
    val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var ownBillsAdapter: OwnBillsAdapter
    private var billList: ArrayList<Bill>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentPaymentHistoryBinding.inflate(inflater, container, false)

        getAllOwnBills()

//        val backCallback = object: OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                findNavController().navigate(R.id.studentDashboardFragment)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)

        return binding.root
    }

    private fun getAllOwnBills(){
        val billCoroutineScope = CoroutineScope(Dispatchers.Main)
        billCoroutineScope.launch {
            billList = BillAccess(requireContext(), profileViewModel).getAllOwnBills()

            if(!billList.isNullOrEmpty()){
                billList!!.reverse()
                binding.recyclerViewInPaymentHistory.layoutManager = LinearLayoutManager(activity)
                ownBillsAdapter = OwnBillsAdapter(billList!!,requireContext(),profileViewModel)
                binding.recyclerViewInPaymentHistory.adapter = ownBillsAdapter
            }
            else{
                binding.recyclerViewInPaymentHistory.visibility = View.INVISIBLE
                Toast.makeText(context, "No bills are available", Toast.LENGTH_LONG).show()
            }
        }
    }
}