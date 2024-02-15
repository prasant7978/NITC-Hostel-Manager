package com.kumar.nitchostelmanager.students.fragments

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.Validation
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.students.access.ManageStudentAccess
import com.kumar.nitchostelmanager.databinding.FragmentAddStudentBinding
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Calendar

class AddStudentFragment : Fragment(), CircleLoadingDialog, Validation {
    private lateinit var binding: FragmentAddStudentBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private var genderSelected = "Male"
    private var dob = "NA"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddStudentBinding.inflate(inflater, container, false)

        if(sharedViewModel.viewingStudentRoll != null){
            binding.addStudentButtonInAddStudentFragment.text = "Update Student"
            binding.headingTVInAddStudentFragment.text = "Update Student"

            if(profileViewModel.userType == "Admin"){
                binding.addStudentButtonInAddStudentFragment.visibility = View.VISIBLE
                binding.buttonClearAll.visibility = View.VISIBLE
                binding.dobButtonInAddStudentFragment.isEnabled = true
                binding.nameInputInAddStudentFragment.isEnabled = true
                binding.phoneInputInAddStudentFragment.isEnabled = true
                binding.parentPhoneInAddStudentFragment.isEnabled = true
                binding.emailInputInAddStudentFragment.isEnabled = true
                binding.addressInputInAddStudentFragment.isEnabled = true
                binding.genderRadioGroupInAddStudentFragment.visibility = View.VISIBLE
            }else{
                binding.addStudentButtonInAddStudentFragment.visibility = View.GONE
                binding.buttonClearAll.visibility = View.GONE
                binding.dobButtonInAddStudentFragment.isEnabled = false
                binding.nameInputInAddStudentFragment.isEnabled = false
                binding.phoneInputInAddStudentFragment.isEnabled = false
                binding.parentPhoneInAddStudentFragment.isEnabled = false
                binding.emailInputInAddStudentFragment.isEnabled = false
                binding.addressInputInAddStudentFragment.isEnabled = false
                binding.genderLayoutInAddStudent.visibility = View.GONE
            }

            getStudentDetails(sharedViewModel.viewingStudentRoll!!)
        }else{
            binding.addStudentButtonInAddStudentFragment.text = "Add Student"
            binding.headingTVInAddStudentFragment.text = "Add Student"
        }

        binding.dobButtonInAddStudentFragment.setOnClickListener {
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(
                requireContext(),
                {view,yearChosen,monthChosen,dayChosen->
                    if(monthChosen >= 9)dob = (dayChosen.toString() + "/" + (monthChosen + 1) + "/" + yearChosen)
                    else dob = (dayChosen.toString() + "/0" + (monthChosen + 1) + "/" + yearChosen)
                    binding.dobButtonInAddStudentFragment.text = dob
                },
                year,
                month,
                day
            ).show()
        }

        binding.addStudentButtonInAddStudentFragment.setOnClickListener {
            val studentName = binding.nameInputInAddStudentFragment.text.toString()
            val studentEmail = binding.emailInputInAddStudentFragment.text.toString()
            val studentPhone = binding.phoneInputInAddStudentFragment.text.toString()
            val studentParentPhone = binding.parentPhoneInAddStudentFragment.text.toString()
            val studentAddress = binding.addressInputInAddStudentFragment.text.toString()
            genderSelected = (requireActivity().findViewById<RadioButton>(binding.genderRadioGroupInAddStudentFragment.checkedRadioButtonId)).text.toString()

            if(studentName!!.isEmpty() || studentEmail!!.isEmpty() || studentPhone!!.isEmpty() || studentParentPhone!!.isEmpty() || dob == "NA" || studentAddress!!.isEmpty()){
                Toast.makeText(activity,"Please provide complete information", Toast.LENGTH_SHORT).show()
            }
            else if(!(checkValidPhoneNumber(studentPhone)) || !(checkValidPhoneNumber(studentParentPhone))){
                Toast.makeText(activity,"Please Enter A Valid Phone Number", Toast.LENGTH_SHORT).show()
            }
            else if(!checkValidEmail(studentEmail) && !checkValidNITCEmail(studentEmail)){
                Toast.makeText(context,"Enter a valid NITC email id", Toast.LENGTH_SHORT).show()
            }
            else if(!checkValidName(studentName)){
                Toast.makeText(context,"Enter A Valid Name", Toast.LENGTH_SHORT).show()
            }
            else {
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

                binding.addStudentButtonInAddStudentFragment.isCheckable = false
                binding.progressBarInAddStudentFragment.visibility = View.VISIBLE

                val student: Student = Student(
                    studentRoll,
                    studentEmail,
                    studentPassword,
                    studentName,
                    studentPhone,
                    studentParentPhone,
                    genderSelected,
                    dob,
                    0.0,
                    studentAddress,
                    courseEnrolled
                )

                if(sharedViewModel.viewingStudentRoll != null) showAlertMessageForUpdate(
                    sharedViewModel.viewingStudentRoll!!,
                    student
                )
                else showAlertMessageForAdd(student)

                binding.addStudentButtonInAddStudentFragment.isCheckable = true
                binding.progressBarInAddStudentFragment.visibility = View.INVISIBLE
            }
        }

        binding.buttonClearAll.setOnClickListener {
            clearAllTextArea()
        }

        val backCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                sharedViewModel.viewingStudentRoll = null
                if(profileViewModel.userType == "Admin") findNavController().navigate(R.id.allStudentsFragment)
                else findNavController().navigate(R.id.occupantsFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)
        return binding.root
    }

    private fun getStudentDetails(viewingStudentRoll: String) {
        val studentCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@AddStudentFragment)
        studentCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val student = ManageStudentAccess(
                requireContext(),
                this@AddStudentFragment,
                profileViewModel
            ).getStudent(viewingStudentRoll)
            loadingDialog.cancel()
            studentCoroutineScope.cancel()
            if(student != null){
                binding.dobButtonInAddStudentFragment.text = student.dob
                if(student.gender == "Male") binding.checkBoxMaleInAddStudentFragment.isChecked = true
                else binding.checkBoxFemaleInAddStudentFragment.isChecked = true
                genderSelected = student.gender
                dob = student.dob
                binding.nameInputInAddStudentFragment.setText(student.name)
                binding.emailInputInAddStudentFragment.setText(student.email)
                binding.parentPhoneInAddStudentFragment.setText(student.parentPhone)
                binding.phoneInputInAddStudentFragment.setText(student.phone)
                binding.addressInputInAddStudentFragment.setText(student.address)
            }
        }
    }

    private fun showAlertMessageForUpdate(studentRoll: String,student: Student){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Update Student")
        dialog?.setMessage("Are you sure you want to update this student?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            updateStudent(studentRoll,student)
        })
        dialog?.create()?.show()
    }

    private fun updateStudent(studentRoll:String,student: Student) {
        val manageStudentCoroutineScope = CoroutineScope(Dispatchers.Main)
        manageStudentCoroutineScope.launch {
            val updatedStudent = ManageStudentAccess(
                requireContext(),
                this@AddStudentFragment,
                profileViewModel
            ).updateStudent(studentRoll,student)
            manageStudentCoroutineScope.cancel()

            if(updatedStudent != null){
                Snackbar.make(binding.linearLayout,"Student account updated", Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                findNavController().navigate(R.id.allStudentsFragment)
            }
            else
                Snackbar.make(binding.linearLayout,"Oops! We encountered a problem while updating student, please try again", Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
        }
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

    private fun clearAllTextArea(){
        binding.nameInputInAddStudentFragment.setText("")
        binding.emailInputInAddStudentFragment.setText("")
        binding.phoneInputInAddStudentFragment.setText("")
        binding.parentPhoneInAddStudentFragment.setText("")
        binding.dobButtonInAddStudentFragment.setText("Date fo Birth")
        binding.checkBoxMaleInAddStudentFragment.isChecked = true
        binding.addressInputInAddStudentFragment.setText("")
        genderSelected = "NA"
        dob = "NA"
    }
}