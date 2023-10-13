package com.kumar.nitchostelmanager.wardens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {
    lateinit var binding: FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)



        return binding.root
    }

}