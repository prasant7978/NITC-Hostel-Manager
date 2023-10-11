package com.kumar.nitchostelmanager.bills.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentWardenGenerateBillBinding

class WardenGenerateBillFragment : Fragment() {
    lateinit var binding: FragmentWardenGenerateBillBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWardenGenerateBillBinding.inflate(inflater, container, false)



        val backCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.wardenDashboardFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)

        return binding.root
    }
}