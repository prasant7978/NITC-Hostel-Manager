package com.kumar.nitchostelmanager.admin.fragments

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
import com.kumar.nitchostelmanager.ProfileViewModel
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.admin.access.ManageStudentAccess
import com.kumar.nitchostelmanager.databinding.FragmentAddStudentBinding
import com.kumar.nitchostelmanager.models.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AddStudentFragment : Fragment() {
    private lateinit var addStudentBinding: FragmentAddStudentBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addStudentBinding = FragmentAddStudentBinding.inflate(inflater, container, false)

        addStudentBinding.buttonAddStudent.setOnClickListener {
            val studentName = addStudentBinding.textInputName.text.toString()
            val studentEmail = addStudentBinding.textInputEmail.text.toString()
            val studentPhone = addStudentBinding.textInputPhone.text.toString()
            val studentParentPhone = addStudentBinding.textInputParentPhone.text.toString()
            val studentGender = addStudentBinding.textInputGender.text.toString()
            val studentDOB = addStudentBinding.textInputDob.text.toString()
            val studentAddress = addStudentBinding.textInputAddress.text.toString()

            // obtain rollno from email
            val str: List<String>? = studentEmail?.split("_")
            val roll = str?.get(1)?.split("@")
            val studentRoll = roll?.get(0).toString()
            Log.d("roll", studentRoll)

            // obtain course from email
            var courseEnrolled = studentRoll.substring(0, 1) + studentRoll.substring(studentRoll.length - 3)
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
                    addStudentBinding.buttonAddStudent.isCheckable = false
                    addStudentBinding.progressBar.visibility = View.VISIBLE

                    val student: Student = Student(studentRoll, studentEmail, studentPassword, studentName, studentPhone, studentParentPhone, studentGender, studentDOB, 0.0, studentAddress, courseEnrolled)

                    showAlertMessageForAdd(student)

                    addStudentBinding.buttonAddStudent.isCheckable = true
                    addStudentBinding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

        addStudentBinding.buttonClearAll.setOnClickListener {
            clearAllTextArea()
        }

        return addStudentBinding.root
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
            val addedStudent: Boolean = ManageStudentAccess(requireContext(), this@AddStudentFragment, profileViewModel).addStudent(student)
            manageStudentCoroutineScope.cancel()

            if(addedStudent){
                Snackbar.make(addStudentBinding.linearLayout,"Student account created", Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                clearAllTextArea()
            }
            else
                Snackbar.make(addStudentBinding.linearLayout,"Oops! We encountered a problem while creating your account, please try again", Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
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
        addStudentBinding.textInputName.setText("")
        addStudentBinding.textInputEmail.setText("")
        addStudentBinding.textInputPhone.setText("")
        addStudentBinding.textInputParentPhone.setText("")
        addStudentBinding.textInputGender.setText("")
        addStudentBinding.textInputDob.setText("")
        addStudentBinding.textInputAddress.setText("")
    }
}