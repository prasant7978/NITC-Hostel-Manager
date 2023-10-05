package com.kumar.nitchostelmanager.admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.admin.access.ManageStudentAccess
import com.kumar.nitchostelmanager.admin.adapters.StudentListAdapter
import com.kumar.nitchostelmanager.databinding.FragmentStudentListBinding
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class StudentListFragment : Fragment() {
    private lateinit var studentListBinding: FragmentStudentListBinding
    private lateinit var studentListAdapter: StudentListAdapter
    private var studentList = ArrayList<Student>()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        studentListBinding = FragmentStudentListBinding.inflate(inflater, container, false)

        retrieveAllStudents()

        studentListBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    studentListBinding.search.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchList(newText)
                    return true
                }
            }
        )

        return studentListBinding.root
    }

    private fun retrieveAllStudents(){
        val manageStudentCoroutineScope = CoroutineScope(Dispatchers.Main)
        manageStudentCoroutineScope.launch {
            studentList = ManageStudentAccess(requireContext(), this@StudentListFragment, profileViewModel).getStudents(studentListBinding.constraintLayout)!!
            manageStudentCoroutineScope.cancel()

            if(studentList != null){
                studentList.reverse()
                studentListBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
                studentListAdapter = StudentListAdapter(studentList, sharedViewModel, this@StudentListFragment)
                studentListBinding.recyclerView.adapter = studentListAdapter
            }
            else{
                studentListBinding.search.visibility = View.INVISIBLE
            }
        }
    }

    private fun searchList(text: String){
        val searchList = ArrayList<Student>()
        for(student in studentList){
            if(student.studentRoll.lowercase().contains(text.lowercase())){
                searchList.add(student)
            }
        }
        studentListAdapter.searchByRollNo(searchList)
    }
}