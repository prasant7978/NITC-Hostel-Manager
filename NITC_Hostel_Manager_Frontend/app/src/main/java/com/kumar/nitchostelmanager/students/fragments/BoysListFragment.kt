package com.kumar.nitchostelmanager.students.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.students.access.ManageStudentAccess
import com.kumar.nitchostelmanager.students.adapter.BoysListAdapter
import com.kumar.nitchostelmanager.databinding.FragmentBoysListBinding
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import com.kumar.nitchostelmanager.viewModel.ViewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BoysListFragment : Fragment(), CircleLoadingDialog {
    private lateinit var binding: FragmentBoysListBinding
    private lateinit var boysListAdapter: BoysListAdapter
    private var boysList = ArrayList<Student>()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val viewsViewModel: ViewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_boys_list, container, false)

        binding.lifecycleOwner = this
        binding.mainViewModel = viewsViewModel
        viewsViewModel.updateLoadingState(true)

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
        val loadingDialog = getLoadingDialog(requireContext(), this)

        manageStudentCoroutineScope.launch {
            viewsViewModel.updateLoadingState(true)
            loadingDialog.create()
            loadingDialog.show()

            boysList = ManageStudentAccess(requireContext(), this@BoysListFragment, profileViewModel).getBoys(binding.constraintLayout)!!

            loadingDialog.cancel()
            viewsViewModel.updateLoadingState(false)
            manageStudentCoroutineScope.cancel()

            if(boysList != null){
                boysList.reverse()
                binding.recyclerViewInBoysListFragment.layoutManager = LinearLayoutManager(activity)
                boysListAdapter = BoysListAdapter(
                    requireContext(),
                    profileViewModel,
                    boysList,
                    this@BoysListFragment,
                    {studentIndex->
                        if(boysList.size> studentIndex) {
                            sharedViewModel.viewingStudentRoll = boysList[studentIndex].studentRoll
                            findNavController().navigate(R.id.addStudentFragment)
                        }
                    },
                    {studentIndex->
                        if(boysList.size> studentIndex) deleteStudent(boysList[studentIndex].studentRoll)
                    }
                )
                binding.recyclerViewInBoysListFragment.adapter = boysListAdapter
            }
            else{
                binding.searchViewInBoysListFragment.visibility = View.INVISIBLE
            }
        }

    }

    private fun deleteStudent(studentRoll:String) {
        val deleteCoroutineScope = CoroutineScope(Dispatchers.Main)
        deleteCoroutineScope.launch {
            val deleted = ManageStudentAccess(
                requireContext(),
                this@BoysListFragment,
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
        for(student in boysList){
            if(student.studentRoll.lowercase().contains(text.lowercase())){
                searchList.add(student)
            }
        }
        boysListAdapter.searchByRollNo(searchList)
    }
}