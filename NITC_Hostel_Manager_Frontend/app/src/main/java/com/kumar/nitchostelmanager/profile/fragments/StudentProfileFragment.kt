package com.kumar.nitchostelmanager.profile.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentStudentProfileBinding

class StudentProfileFragment : Fragment() {

    private lateinit var binding:FragmentStudentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStudentProfileBinding.inflate(inflater,container,false)

        return binding.root
    }
}