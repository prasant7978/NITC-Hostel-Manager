package com.kumar.nitchostelmanager.wardens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.wardens.access.ManageWardensAccess
import com.kumar.nitchostelmanager.wardens.adapters.WardenListAdapter
import com.kumar.nitchostelmanager.databinding.FragmentWardenListBinding
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import com.kumar.nitchostelmanager.wardens.access.WardensDataAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class WardenListFragment : Fragment() {
    private lateinit var binding: FragmentWardenListBinding
    private lateinit var wardenListAdapter: WardenListAdapter
    private var wardenList: ArrayList<Warden>? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWardenListBinding.inflate(inflater, container, false)

        retrieveAllWardens()

        binding.searchInWardenListFragment.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.searchInWardenListFragment.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (!wardenList.isNullOrEmpty()) searchList(newText)
                return true
            }
        }
        )

        binding.addWardenButtonInWardenListFragment.setOnClickListener {
            findNavController().navigate(R.id.addWardenFragment)
        }

        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.adminDashboardFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)

        return binding.root
    }

    private fun deleteWarden(wardenEmail: String, hostelID: String) {
        val deleteCoroutineScope = CoroutineScope(Dispatchers.Main)
        deleteCoroutineScope.launch {
            val deleted = ManageWardensAccess(
                requireContext(),
                this@WardenListFragment,
                profileViewModel
            ).deleteWarden(wardenEmail, hostelID)

            deleteCoroutineScope.cancel()
            if (deleted) {
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                retrieveAllWardens()
            }
        }

    }

    private fun retrieveAllWardens() {
        val manageWardenCoroutineScope = CoroutineScope(Dispatchers.Main)
        manageWardenCoroutineScope.launch {
            wardenList = WardensDataAccess(
                requireContext(),
                profileViewModel.loginToken.toString()
            ).getWardens(binding.constraintLayout)
            manageWardenCoroutineScope.cancel()
            if (!wardenList.isNullOrEmpty()) {
                wardenList!!.reverse()
                binding.recyclerViewInWardenListFragment.layoutManager =
                    LinearLayoutManager(requireContext())
                wardenListAdapter = WardenListAdapter(
                    requireContext(),
                    profileViewModel,
                    wardenList!!,
                    { wardenEmail ->
                        sharedViewModel.viewingWardenEmail = wardenEmail
                        findNavController().navigate(R.id.addWardenFragment)
                    },
                    { wardenPos ->
                        deleteWarden(
                            wardenList!![wardenPos].email,
                            wardenList!![wardenPos].hostelID
                        )
                    }
                )
                binding.noWardensTVInWardenListFragment.visibility = View.GONE
                binding.recyclerViewInWardenListFragment.visibility = View.VISIBLE
                binding.recyclerViewInWardenListFragment.adapter = wardenListAdapter
            } else {
                binding.noWardensTVInWardenListFragment.visibility = View.VISIBLE
                binding.recyclerViewInWardenListFragment.visibility = View.GONE
            }
        }
    }

    private fun searchList(text: String) {
        val searchList = ArrayList<Warden>()
        for (warden in wardenList!!) {
            if (warden.name.lowercase().contains(text.lowercase())) {
                searchList.add(warden)
            }
        }
        wardenListAdapter.searchByWardenName(searchList)
    }
}