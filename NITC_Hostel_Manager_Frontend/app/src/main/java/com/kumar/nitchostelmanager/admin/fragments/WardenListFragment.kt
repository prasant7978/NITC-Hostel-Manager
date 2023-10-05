package com.kumar.nitchostelmanager.admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.admin.access.ManageWardensAccess
import com.kumar.nitchostelmanager.admin.adapters.WardenListAdapter
import com.kumar.nitchostelmanager.databinding.FragmentWardenListBinding
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class WardenListFragment : Fragment() {
    private lateinit var wardenListBinding: FragmentWardenListBinding
    private lateinit var wardenListAdapter: WardenListAdapter
    private var wardenList = ArrayList<Warden>()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wardenListBinding = FragmentWardenListBinding.inflate(inflater, container, false)

        retrieveAllWardens()

        wardenListBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    wardenListBinding.search.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchList(newText)
                    return true
                }
            }
        )

        return wardenListBinding.root
    }

    private fun retrieveAllWardens(){
        val manageWardenCoroutineScope = CoroutineScope(Dispatchers.Main)
        manageWardenCoroutineScope.launch {
            wardenList = ManageWardensAccess(requireContext(), this@WardenListFragment, profileViewModel).getWardens(wardenListBinding.constraintLayout)!!
            manageWardenCoroutineScope.cancel()

            if(wardenList != null){
                wardenList.reverse()
                wardenListBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
                wardenListAdapter = WardenListAdapter(wardenList, sharedViewModel, this@WardenListFragment)
                wardenListBinding.recyclerView.adapter = wardenListAdapter
            }
            else{
                wardenListBinding.search.visibility = View.INVISIBLE
            }
        }
    }

    private fun searchList(text: String){
        val searchList = ArrayList<Warden>()
        for(warden in wardenList){
            if(warden.name.lowercase().contains(text.lowercase())){
                searchList.add(warden)
            }
        }
        wardenListAdapter.searchByWardenName(searchList)
    }
}