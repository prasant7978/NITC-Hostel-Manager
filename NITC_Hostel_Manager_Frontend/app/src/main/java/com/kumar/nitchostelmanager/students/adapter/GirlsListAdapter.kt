package com.kumar.nitchostelmanager.students.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.StudentCardBinding
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.viewModel.SharedViewModel

class GirlsListAdapter(private var studentList: ArrayList<Student>, private var sharedViewModel: SharedViewModel, private var parentFragment: Fragment): RecyclerView.Adapter<GirlsListAdapter.StudentViewHolder>() {
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
        holder.adapterBinding.studentNameInStudentCard.text = studentList[position].name
        holder.adapterBinding.studentRollInStudentCard.text = studentList[position].studentRoll
        holder.adapterBinding.studentEmailInStudentCard.text = studentList[position].email
        holder.adapterBinding.hostelNameInStudentCard.text = studentList[position].hostelID
        holder.adapterBinding.roomNumberInStudentCard.text = studentList[position].roomNumber.toString()

        holder.adapterBinding.constraintLayoutInStudentCard.setOnClickListener {
            sharedViewModel.viewingStudentRoll = studentList[position].studentRoll

            parentFragment.findNavController().navigate(R.id.viewStudentFragment)
        }
    }

    fun searchByRollNo(searchList : ArrayList<Student>){
        studentList = searchList
        notifyDataSetChanged()
    }
}