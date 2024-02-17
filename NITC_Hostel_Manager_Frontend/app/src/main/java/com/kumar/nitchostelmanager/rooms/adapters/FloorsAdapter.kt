package com.kumar.nitchostelmanager.rooms.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FloorCardBinding

class FloorsAdapter(
    var context:Context,
    var totalFloors:Int,
    var selected:Array<Boolean>,
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
        if(selected[position]){
            holder.binding.floorCardInFloorCard.setBackgroundResource(R.color.purple_700)
            holder.binding.floorInFloorCard.setTextColor(ContextCompat.getColor(context,R.color.white))
        }else{
            holder.binding.floorCardInFloorCard.setBackgroundResource(R.color.white)
            holder.binding.floorInFloorCard.setTextColor(ContextCompat.getColor(context,R.color.black))
        }
        holder.binding.floorCardInFloorCard.setOnClickListener {
            for(i in 0..<totalFloors){
                selected[i] = false
            }
            selected[position] = true
            callback(position)
            notifyDataSetChanged()
        }
    }
}