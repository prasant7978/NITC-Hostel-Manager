package com.kumar.nitchostelmanager.complaints.fragments

import android.content.DialogInterface
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.complaints.access.ComplaintsDataAccess
import com.kumar.nitchostelmanager.complaints.access.ManageComplaintAccess
import com.kumar.nitchostelmanager.complaints.adapters.AllComplaintsAdapter
import com.kumar.nitchostelmanager.databinding.FragmentViewAllComplaintsBinding
import com.kumar.nitchostelmanager.models.Complaint
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class ViewAllComplaintsFragment : Fragment() {
    private lateinit var binding: FragmentViewAllComplaintsBinding
    private lateinit var complaintsAdapter: AllComplaintsAdapter
    private var complaintsList: ArrayList<Complaint>? = null
    val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAllComplaintsBinding.inflate(inflater, container, false)

        getAllComplaints()

        val rejectColor = ContextCompat.getColor(requireContext(), R.color.light_red)
        val resolveColor = ContextCompat.getColor(requireContext(), R.color.light_green)

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(complaintsList?.get(viewHolder.bindingAdapterPosition)!!.status == "Pending")
                    showDialogForReject(viewHolder.bindingAdapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(rejectColor)
                    .addSwipeLeftLabel("Reject")
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }).attachToRecyclerView(binding.recyclerViewInViewAllComplaints)

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(complaintsList!![viewHolder.absoluteAdapterPosition].status == "Pending")
                    showDialogForResolve(viewHolder.bindingAdapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(resolveColor)
                    .addSwipeRightLabel("Resolve")
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }).attachToRecyclerView(binding.recyclerViewInViewAllComplaints)

        val backCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.wardenDashboardFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)

        return binding.root
    }

    private fun getAllComplaints(){
        val complaintsCoroutineScope = CoroutineScope(Dispatchers.Main)
        complaintsCoroutineScope.launch {
            complaintsList = ComplaintsDataAccess(
                requireContext(),
                this@ViewAllComplaintsFragment,
                profileViewModel.loginToken.toString()
            ).viewAllComplaints(profileViewModel.currentWarden.hostelID.toString())
            complaintsCoroutineScope.cancel()

            if(!complaintsList.isNullOrEmpty()){
                complaintsList!!.reverse()
                binding.recyclerViewInViewAllComplaints.layoutManager = LinearLayoutManager(activity)
                complaintsAdapter = AllComplaintsAdapter(complaintsList!!, requireContext())
                binding.recyclerViewInViewAllComplaints.adapter = complaintsAdapter
            }
            else {
                binding.recyclerViewInViewAllComplaints.visibility = View.INVISIBLE
                Toast.makeText(context, "No complaints are available", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showDialogForReject(position: Int){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Reject Complaint")
        dialog?.setMessage("Are you sure you want to reject this complaint?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            rejectComplaint(position)
        })
        dialog?.create()?.show()
    }

    private fun rejectComplaint(position: Int){
        val complaintCoroutineScope = CoroutineScope(Dispatchers.Main)
        complaintCoroutineScope.launch {
            if(!complaintsList.isNullOrEmpty()) {
                val rejectComplaint = ManageComplaintAccess(requireContext(), profileViewModel).rejectComplaint(complaintsList!![position].complaintID)
                complaintCoroutineScope.cancel()

                if(rejectComplaint){
                    Snackbar.make(
                        binding.layoutInViewAllComplaints,
                        "The complaint has been rejected",
                        Snackbar.LENGTH_SHORT
                    ).setAction("Close", View.OnClickListener { }).show()

                    getAllComplaints()
                }
            }
        }
    }

    private fun showDialogForResolve(position: Int){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Resolve Complaint")
        dialog?.setMessage("Are you sure you want to resolve this complaint?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            resolveComplaint(position)
        })
        dialog?.create()?.show()
    }

    private fun resolveComplaint(position: Int){
        val complaintCoroutineScope = CoroutineScope(Dispatchers.Main)
        complaintCoroutineScope.launch {
            if(!complaintsList.isNullOrEmpty()) {
                val resolveComplaint = ManageComplaintAccess(requireContext(), profileViewModel).resolveComplaint(complaintsList!![position].complaintID)
                complaintCoroutineScope.cancel()

                if(resolveComplaint){
                    Snackbar.make(
                        binding.layoutInViewAllComplaints,
                        "The complaint has been resolved",
                        Snackbar.LENGTH_SHORT
                    ).setAction("Close", View.OnClickListener { }).show()

                    getAllComplaints()
                }
            }
        }
    }
}