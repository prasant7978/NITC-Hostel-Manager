package com.kumar.nitchostelmanager.students.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentOccupantsBinding
import com.kumar.nitchostelmanager.hostels.access.HostelDataAccess
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.students.adapter.OccupantsAdapter
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OccupantsFragment:Fragment(),CircleLoadingDialog {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val profileViewModel:ProfileViewModel by activityViewModels()

    private lateinit var binding:FragmentOccupantsBinding
    private var occupantsAdapter:OccupantsAdapter? = null
    private var occupants:List<Student>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOccupantsBinding.inflate(inflater,container,false)

        getOccupants()

        binding.searchViewInOccupantsFragment.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(occupants != null) if(occupants!!.map{it.studentRoll}.contains(query)){
                    if(occupantsAdapter != null) {
                        occupantsAdapter!!.searchByRollNo(occupants!!.filter { it.studentRoll.equals(query) })
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(occupants != null)
                    if(occupantsAdapter != null) {
                        occupantsAdapter!!.searchByRollNo(occupants!!.filter { it.studentRoll.contains(
                            newText.toString()
                        ) })

                }
                return false
            }

        })


        return binding.root
    }

    private fun getOccupants() {
        val getOccupantsCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@OccupantsFragment)
        loadingDialog.create()
        getOccupantsCoroutineScope.launch {
            loadingDialog.show()
            occupants = HostelDataAccess(
                requireContext(),
                this@OccupantsFragment,
                profileViewModel.loginToken.toString()
            ).getHostelOccupants(profileViewModel.currentWarden.hostelID)
            loadingDialog.cancel()
            getOccupantsCoroutineScope.cancel()
            if(!occupants.isNullOrEmpty()){
                binding.recyclerViewInOccupantsFragment.layoutManager = LinearLayoutManager(context)
                occupantsAdapter = OccupantsAdapter(
                    occupants!!,
                ){position->
                    if(occupants!!.size> position){
                        sharedViewModel.viewingStudentRoll = occupants!![position].studentRoll
                        findNavController().navigate(R.id.addStudentFragment)
                    }

                }
                binding.recyclerViewInOccupantsFragment.adapter = occupantsAdapter
            }
        }
    }

}