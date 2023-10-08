package com.kumar.nitchostelmanager.wardens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.WardenCardBinding
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.viewModel.SharedViewModel

class WardenListAdapter(
    private var wardenList: ArrayList<Warden>,
    private var sharedViewModel: SharedViewModel,
    private var parentFragment: Fragment
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
        holder.adapterBinding.wardenName.text = wardenList[position].name
        holder.adapterBinding.wardenEmail.text = wardenList[position].email
        holder.adapterBinding.wardenPhone.text = wardenList[position].phone
        holder.adapterBinding.hostelName.text = wardenList[position].hostelID

        holder.adapterBinding.constraintLayout.setOnClickListener {
            sharedViewModel.viewingWardenEmail = wardenList[position].email

            parentFragment.findNavController().navigate(R.id.viewWardenFragment)
        }
    }

    fun searchByWardenName(searchList : ArrayList<Warden>){
        wardenList = searchList
        notifyDataSetChanged()
    }
}