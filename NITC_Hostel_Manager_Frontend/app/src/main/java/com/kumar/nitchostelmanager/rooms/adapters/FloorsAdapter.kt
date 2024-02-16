package com.kumar.nitchostelmanager.rooms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FloorCardBinding

class FloorsAdapter(
    var totalFloors:Int,
    var callback:(Int)->Unit
) : RecyclerView.Adapter<FloorsAdapter.FloorViewHolder>(){
    class FloorViewHolder(val binding:FloorCardBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorViewHolder {
        val binding = FloorCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FloorViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return totalFloors
    }

    override fun onBindViewHolder(holder: FloorViewHolder, position: Int) {
        holder.binding.floorInFloorCard.text = "Floor ${position}"
        holder.binding.floorCardInFloorCard.setOnClickListener {
//            holder.binding.floorCardInFloorCard.setBackgroundResource(R.drawable.login_type_shape)
            callback(position)
        }
    }
}