package com.kumar.nitchostelmanager.students.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.students.access.ManageStudentAccess
import com.kumar.nitchostelmanager.databinding.FragmentAddStudentBinding
import com.kumar.nitchostelmanager.models.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AddStudentFragment : Fragment() {
    private lateinit var binding: FragmentAddStudentBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddStudentBinding.inflate(inflater, container, false)

        binding.addStudentButton.setOnClickListener {
            val studentName = binding.nameInputInAddStudentFragment.text.toString()
            val studentEmail = binding.emailInputInAddStudentFragment.text.toString()
            val studentPhone = binding.phoneInputInAddStudentFragment.text.toString()
            val studentParentPhone = binding.parentPhoneInAddStudentFragment.text.toString()
            val studentGender = binding.genderInputInAddStudentFragment.text.toString()
            val studentDOB = binding.dobInputInAddStudentFragment.text.toString()
            val studentAddress = binding.addressInputInAddStudentFragment.text.toString()

            // obtain rollno from email
            val str: List<String>? = studentEmail?.split("_")
            val roll = str?.get(1)?.split("@")
            val studentRoll = roll?.get(0).toString()
            Log.d("roll", studentRoll)

            // obtain course from email
            var courseEnrolled = studentRoll.substring(0, 1) + studentRoll.substring(studentRoll.length - 2)
            courseEnrolled = courseEnrolled.uppercase()
            Log.d("course", courseEnrolled)

            val studentPassword = studentRoll

            if(studentName!!.isEmpty() || studentEmail!!.isEmpty() || studentPhone!!.isEmpty() || studentParentPhone!!.isEmpty() || studentGender!!.isEmpty() || studentDOB!!.isEmpty() || studentAddress!!.isEmpty()){
                Toast.makeText(activity,"Please provide complete information", Toast.LENGTH_SHORT).show()
            }
            else {
                if(!checkConstraints(studentEmail)){
                    Toast.makeText(context,"Enter a valid nitc email id", Toast.LENGTH_SHORT).show()
                }
                else {
                    binding.addStudentButton.isCheckable = false
                    binding.progressBarInAddStudentFragment.visibility = View.VISIBLE

                    val student: Student = Student(
                        studentRoll,
                        studentEmail,
                        studentPassword,
                        studentName,
                        studentPhone,
                        studentParentPhone,
                        studentGender,
                        studentDOB,
                        0.0,
                        studentAddress,
                        courseEnrolled
                    )

                    showAlertMessageForAdd(student)

                    binding.addStudentButton.isCheckable = true
                    binding.progressBarInAddStudentFragment.visibility = View.INVISIBLE
                }
            }
        }

        binding.buttonClearAll.setOnClickListener {
            clearAllTextArea()
        }

        return binding.root
    }

    private fun showAlertMessageForAdd(student: Student){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Add Student")
        dialog?.setMessage("Are you sure you want to add this student?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            addStudent(student)
        })
        dialog?.create()?.show()
    }

    private fun addStudent(student: Student){
        val manageStudentCoroutineScope = CoroutineScope(Dispatchers.Main)
        manageStudentCoroutineScope.launch {
            val addedStudent: Boolean = ManageStudentAccess(
                requireContext(),
                this@AddStudentFragment,
                profileViewModel
            ).addStudent(student)
            manageStudentCoroutineScope.cancel()

            if(addedStudent){
                Snackbar.make(binding.linearLayout,"Student account created", Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                clearAllTextArea()
            }
            else
                Snackbar.make(binding.linearLayout,"Oops! We encountered a problem while creating your account, please try again", Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
        }
    }

    private fun checkConstraints(email: String): Boolean {
        if(email.contains('_')) {
            val roll = email.substring(email.indexOf("_") + 1, email.length)
            if (roll[0] == 'm' || roll[0] == 'b' || roll[0] == 'p') {
                if(roll.contains('@')) {
                    val domain = roll.substring(roll.indexOf("@") + 1, roll.length)
                    return domain == "nitc.ac.in"
                }
                else{
                    return false
                }
            } else {
                return false
            }
        }
        else{
            return false
        }
    }

    private fun clearAllTextArea(){
        binding.nameInputInAddStudentFragment.setText("")
        binding.emailInputInAddStudentFragment.setText("")
        binding.phoneInputInAddStudentFragment.setText("")
        binding.parentPhoneInAddStudentFragment.setText("")
        binding.genderInputInAddStudentFragment.setText("")
        binding.dobInputInAddStudentFragment.setText("")
        binding.addressInputInAddStudentFragment.setText("")
    }
}