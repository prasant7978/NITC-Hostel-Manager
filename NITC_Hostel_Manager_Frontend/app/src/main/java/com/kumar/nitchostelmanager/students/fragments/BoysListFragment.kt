package com.kumar.nitchostelmanager.students.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.students.access.ManageStudentAccess
import com.kumar.nitchostelmanager.students.adapter.BoysListAdapter
import com.kumar.nitchostelmanager.databinding.FragmentBoysListBinding
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BoysListFragment : Fragment() {
    private lateinit var binding: FragmentBoysListBinding
    private lateinit var boysListAdapter: BoysListAdapter
    private var boysList = ArrayList<Student>()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoysListBinding.inflate(inflater, container, false)

        retrieveAllStudents()

        binding.searchViewInBoysListFragment.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    binding.searchViewInBoysListFragment.clearFocus()
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
            boysList = ManageStudentAccess(requireContext(), this@BoysListFragment, profileViewModel).getBoys(binding.constraintLayout)!!
            manageStudentCoroutineScope.cancel()

            if(boysList != null){
                boysList.reverse()
                binding.recyclerViewInBoysListFragment.layoutManager = LinearLayoutManager(activity)
                boysListAdapter = BoysListAdapter(requireContext(),profileViewModel,boysList, sharedViewModel, this@BoysListFragment)
                binding.recyclerViewInBoysListFragment.adapter = boysListAdapter
            }
            else{
                binding.searchViewInBoysListFragment.visibility = View.INVISIBLE
            }
        }
    }

    private fun searchList(text: String){
        val searchList = ArrayList<Student>()
        for(student in boysList){
            if(student.studentRoll.lowercase().contains(text.lowercase())){
                searchList.add(student)
            }
        }
        boysListAdapter.searchByRollNo(searchList)
    }
}