package com.kumar.nitchostelmanager.hostels.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.HostelCardBinding
import com.kumar.nitchostelmanager.hostels.access.ManageHostelsAccess
import com.kumar.nitchostelmanager.models.Hostel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HostelListAdapter(
    var context: Context,
    var loginToken:String,
    var parentFragment: Fragment,
    private var sharedViewModel: SharedViewModel,
    var hostels:ArrayList<Hostel>
) :RecyclerView.Adapter<HostelListAdapter.HostelListViewHolder>(){
    class HostelListViewHolder(val binding:HostelCardBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HostelListViewHolder {
        val binding = HostelCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HostelListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hostels.size
    }

    override fun onBindViewHolder(holder: HostelListViewHolder, position: Int) {
        holder.binding.hostelNameInHostelCard.text = hostels[position].hostelID.toString()
        holder.binding.wardenNameInHostelCard.text = hostels[position].wardenEmail.toString()
        holder.binding.updateWardenButtonInHostelCard.setOnClickListener {
            sharedViewModel.updatingHostelID = hostels[position].hostelID.toString()
            parentFragment.findNavController().navigate(R.id.addHostelFragment)
        }
        holder.binding.hostelCardInHostelCard.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete Hostel")
                .setPositiveButton("Yes"){dialog,which->
                    deleteHostel(position)
                }
                .setNegativeButton("No"){dialog,which->
                    dialog.dismiss()
                }
                .create().show()
            return@setOnLongClickListener true
        }
    }

    private fun deleteHostel(position: Int) {
        val deleteCoroutineScope = CoroutineScope(Dispatchers.Main)
        deleteCoroutineScope.launch {
            val deleted = ManageHostelsAccess(context,loginToken,parentFragment).deleteHostel(hostels[position].hostelID)
            deleteCoroutineScope.cancel()
            if(deleted){
                hostels.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(context,"Hostel deleted",Toast.LENGTH_SHORT).show()
            }
        }
    }
}