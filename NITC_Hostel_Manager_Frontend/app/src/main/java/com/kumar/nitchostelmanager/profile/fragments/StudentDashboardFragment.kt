package com.kumar.nitchostelmanager.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.databinding.FragmentStudentDashboardBinding

class StudentDashboardFragment:Fragment() ,CircleLoadingDialog{

    private lateinit var binding:FragmentStudentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDashboardBinding.inflate(inflater,container,false)

        return binding.root
    }

}