package com.kumar.nitchostelmanager.students.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentGirlsListBinding
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.students.access.ManageStudentAccess
import com.kumar.nitchostelmanager.students.adapter.BoysListAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class GirlsListFragment:Fragment() {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private var girlsList = ArrayList<Student>()
    private lateinit var binding:FragmentGirlsListBinding
    private lateinit var girlsListAdapter:BoysListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGirlsListBinding.inflate(inflater, container, false)

        retrieveAllStudents()

        binding.searchViewInGirlsListFragment.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.searchViewInGirlsListFragment.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        }
        )

        return binding.root
    }

    private fun retrieveAllStudents(){
        val manageStudentCoroutineScope = CoroutineScope(Dispatchers.Main)
        manageStudentCoroutineScope.launch {
            girlsList = ManageStudentAccess(requireContext(), this@GirlsListFragment, profileViewModel).getGirls(binding.constraintLayout)!!
            manageStudentCoroutineScope.cancel()

            if(girlsList != null){
                girlsList.reverse()
                binding.recyclerViewInGirlsListFragment.layoutManager = LinearLayoutManager(activity)
                girlsListAdapter = BoysListAdapter(
                    requireContext(),
                    profileViewModel,
                    girlsList,
                    this@GirlsListFragment,
                    {studentIndex->
                        if(girlsList.size> studentIndex) {
                            sharedViewModel.viewingStudentRoll = girlsList[studentIndex].studentRoll
                            findNavController().navigate(R.id.addStudentFragment)
                        }
                    },
                    {studentIndex->
                        if(girlsList.size> studentIndex) deleteStudent(girlsList[studentIndex].studentRoll)
                    }
                )
                binding.recyclerViewInGirlsListFragment.adapter =  girlsListAdapter
            }
            else{
                binding.searchViewInGirlsListFragment.visibility = View.INVISIBLE
            }
        }
    }

    private fun deleteStudent(studentRoll: String) {
        val deleteCoroutineScope = CoroutineScope(Dispatchers.Main)
        deleteCoroutineScope.launch {
            val deleted = ManageStudentAccess(
                requireContext(),
                this@GirlsListFragment,
                profileViewModel
            ).deleteStudent(
                studentRoll
            )

            deleteCoroutineScope.cancel()
            if(deleted){
                Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchList(text: String){
        val searchList = ArrayList<Student>()
        for(student in girlsList){
            if(student.studentRoll.lowercase().contains(text.lowercase())){
                searchList.add(student)
            }
        }
        girlsListAdapter.searchByRollNo(searchList)
    }

}