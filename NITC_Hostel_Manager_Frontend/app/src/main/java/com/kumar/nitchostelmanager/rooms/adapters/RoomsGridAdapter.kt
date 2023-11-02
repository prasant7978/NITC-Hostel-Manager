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
    var profileViewModel: ProfileViewModel,
    var rooms:ArrayList<Room>,
    var floor:Int,
    var parentFragment:Fragment
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

        if(profileViewModel.userType == "Warden"){
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
                        allocateRoom(position)
                    }
                    .setNegativeButton("No"){dialog,which->
                        dialog.cancel()
                    }
                    .create().show()
            }
        }
    }

    private fun allocateRoom(position: Int){
        val allocateRoomCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(context, parentFragment )
        allocateRoomCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val allocated = ManageRoomAccess(
                context,
                profileViewModel.loginToken.toString(),
                parentFragment
            ).allocateRoom(rooms[position].roomNumber,rooms[position].hostelID)
            loadingDialog.cancel()
            allocateRoomCoroutineScope.cancel()
            if(allocated){
                rooms.clear()
                Toast.makeText(context,"Allocated",Toast.LENGTH_SHORT).show()
                if(profileViewModel.userType == "Student"){

                    parentFragment.findNavController().navigate(R.id.studentDashboardFragment)
                }
                else parentFragment.findNavController().navigate(R.id.allStudentsFragment)
            }
        }
    }

}