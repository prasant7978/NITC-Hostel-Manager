package com.kumar.nitchostelmanager.wardens.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.WardenCardBinding
import com.kumar.nitchostelmanager.hostels.access.ManageHostelsAccess
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.viewModel.SharedViewModel
import com.kumar.nitchostelmanager.wardens.access.ManageWardensAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class WardenListAdapter(
    var context:Context,
    var profileViewModel: ProfileViewModel,
    private var wardenList: ArrayList<Warden>,
    var clickCallback:(String)->Unit,
    var deleteCallback:(String)->Unit
): RecyclerView.Adapter<WardenListAdapter.WardenViewHolder>() {
    class WardenViewHolder(val adapterBinding: WardenCardBinding): RecyclerView.ViewHolder(adapterBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WardenViewHolder {
        val binding = WardenCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WardenViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wardenList.size
    }

    override fun onBindViewHolder(holder: WardenViewHolder, position: Int) {
        holder.adapterBinding.wardenNameInWardenCard.text = wardenList[position].name
        holder.adapterBinding.wardenEmailInWardenCard.text = wardenList[position].email
        holder.adapterBinding.wardenPhoneInWardenCard.text = wardenList[position].phone
        holder.adapterBinding.hostelNameInWardenCard.text = wardenList[position].hostelID

        holder.adapterBinding.constraintLayoutInWardenCard.setOnClickListener {
            clickCallback(wardenList[position].email)
        }
//        holder.adapterBinding.constraintLayoutInWardenCard.setOnClickListener {
//            sharedViewModel.viewingWardenEmail = wardenList[position].email
//
//            parentFragment.findNavController().navigate(R.id.viewWardenFragment)
//        }
        
        holder.adapterBinding.constraintLayoutInWardenCard.setOnLongClickListener {

            AlertDialog.Builder(context)
                .setTitle("Delete Warden")
                .setPositiveButton("Yes"){dialog,which->
                    deleteCallback(wardenList[position].email)
                }
                .setNegativeButton("No"){dialog,which->
                    dialog.dismiss()
                }
                .create().show()
            return@setOnLongClickListener true
        }
    }


    fun searchByWardenName(searchList : ArrayList<Warden>){
        wardenList = searchList
        notifyDataSetChanged()
    }
}