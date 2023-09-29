package com.kumar.nitchostelmanager.admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentAddStudentBinding
import com.kumar.nitchostelmanager.models.Student

class AddStudentFragment : Fragment() {
    private lateinit var addStudentBinding: FragmentAddStudentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addStudentBinding = FragmentAddStudentBinding.inflate(inflater, container, false)

        addStudentBinding.buttonAddStudent.setOnClickListener {
            val studentName = addStudentBinding.textInputName.text
            val studentEmail = addStudentBinding.textInputEmail.text
            val studentPhone = addStudentBinding.textInputPhone.text
            val studentParentPhone = addStudentBinding.textInputParentPhone.text
            val studentGender = addStudentBinding.textInputGender.text
            val studentDOB = addStudentBinding.textInputDob.text
            val studentAddress = addStudentBinding.textInputAddress.text

            //prasanta_m210674ca@nitc.ac.in
            val studentRoll = studentName?.split("_")
//            studentRoll =

            showDialog()
        }

        return addStudentBinding.root
    }

//    private fun verifyStudent(): Boolean{
//
//    }

    private fun showDialog() {

    }

    private fun addStudent(){

    }
}