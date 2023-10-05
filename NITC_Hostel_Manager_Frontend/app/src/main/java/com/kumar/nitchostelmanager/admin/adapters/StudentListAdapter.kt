package com.kumar.nitchostelmanager.admin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.StudentCardBinding
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.viewModel.SharedViewModel

class StudentListAdapter(private var studentList: ArrayList<Student>, private var sharedViewModel: SharedViewModel, private var parentFragment: Fragment): RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {
    class StudentViewHolder(val adapterBinding: StudentCardBinding): RecyclerView.ViewHolder(adapterBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = StudentCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.adapterBinding.studentName.text = studentList[position].name
        holder.adapterBinding.studentRoll.text = studentList[position].studentRoll
        holder.adapterBinding.studentEmail.text = studentList[position].email
        holder.adapterBinding.hostelName.text = studentList[position].hostelID
        holder.adapterBinding.roomNumber.text = studentList[position].roomNumber

        holder.adapterBinding.constraintLayout.setOnClickListener {
            sharedViewModel.viewingStudentRoll = studentList[position].studentRoll

            parentFragment.findNavController().navigate(R.id.viewStudentFragment)
        }
    }

    fun searchByRollNo(searchList : ArrayList<Student>){
        studentList = searchList
        notifyDataSetChanged()
    }
}