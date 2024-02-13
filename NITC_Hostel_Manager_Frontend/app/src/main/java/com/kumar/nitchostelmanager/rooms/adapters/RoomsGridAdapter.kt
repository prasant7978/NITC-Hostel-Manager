package com.kumar.nitchostelmanager.rooms.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.RoomCardBinding
import com.kumar.nitchostelmanager.models.Room
import com.kumar.nitchostelmanager.rooms.access.ManageRoomAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RoomsGridAdapter(
    var context: Context,
    val isStudent:Boolean,
    var rooms:Array<Room>,
    var callback:(Int)->Unit
): RecyclerView.Adapter<RoomsGridAdapter.RoomsGridViewHolder>(),CircleLoadingDialog {
    class RoomsGridViewHolder(val binding:RoomCardBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsGridViewHolder {
        val binding = RoomCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RoomsGridViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: RoomsGridViewHolder, position: Int) {
        holder.binding.roomNumberInRoomCard.text = rooms[position].roomNumber.toString()

        if(!isStudent){
            if(rooms[position].studentRoll != null) holder.binding.roomCardLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.light_red))
            else holder.binding.roomCardLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.light_green))

            holder.binding.roomCardInRoomCard.setOnClickListener {
                if(rooms[position].studentRoll != null){

                }
            }

        }else{
            holder.binding.roomCardInRoomCard.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Select this room")
                    .setPositiveButton("Yes"){dialog,which->
                        callback(rooms[position].roomNumber)
                    }
                    .setNegativeButton("No"){dialog,which->
                        dialog.cancel()
                    }
                    .create().show()
            }
        }
    }


}