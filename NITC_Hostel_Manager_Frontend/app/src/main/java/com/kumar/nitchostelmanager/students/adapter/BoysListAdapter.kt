package com.kumar.nitchostelmanager.students.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.StudentCardBinding
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.students.access.ManageStudentAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import com.kumar.nitchostelmanager.wardens.access.ManageWardensAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BoysListAdapter(
    var context:Context,
    var profileViewModel: ProfileViewModel,
    private var studentList: ArrayList<Student>,
    private var sharedViewModel: SharedViewModel,
    private var parentFragment: Fragment
): RecyclerView.Adapter<BoysListAdapter.StudentViewHolder>() {
    class StudentViewHolder(val binding: StudentCardBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = StudentCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.binding.studentNameInStudentCard.text = studentList[position].name
        holder.binding.studentRollInStudentCard.text = studentList[position].studentRoll
        holder.binding.studentEmailInStudentCard.text = studentList[position].email
        holder.binding.hostelNameInStudentCard.text = studentList[position].hostelID
        holder.binding.roomNumberInStudentCard.text = studentList[position].roomNumber.toString()

        holder.binding.constraintLayoutInStudentCard.setOnClickListener {
            sharedViewModel.viewingStudentRoll = studentList[position].studentRoll

            parentFragment.findNavController().navigate(R.id.viewStudentFragment)
        }
        holder.binding.constraintLayoutInStudentCard.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete Student")
                .setPositiveButton("Yes"){dialog,which->
                    deleteStudent(position)
                }
                .setNegativeButton("No"){dialog,which->
                    dialog.dismiss()
                }
                .create().show()
            return@setOnLongClickListener true
        }
    }

    private fun deleteStudent(position: Int) {
        val deleteCoroutineScope = CoroutineScope(Dispatchers.Main)
        deleteCoroutineScope.launch {
            val deleted = ManageStudentAccess(context,parentFragment, profileViewModel).deleteStudent(studentList[position].studentRoll)

            deleteCoroutineScope.cancel()
            if(deleted){
                Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show()
                studentList.removeAt(position)
                notifyDataSetChanged()
            }
        }

    }

    fun searchByRollNo(searchList : ArrayList<Student>){
        studentList = searchList
        notifyDataSetChanged()
    }
}