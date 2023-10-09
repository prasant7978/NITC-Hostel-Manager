package com.kumar.nitchostelmanager.rooms.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.models.Room
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel

class RoomsGridAdapter(
    var context: Context,
    var profileViewModel: ProfileViewModel,
    var rooms:Array<Room>,
    var floor:Int
):BaseAdapter() {
    override fun getCount(): Int {
        return 100
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.room_card,parent,false)

        val roomNumber = view.findViewById<TextView>(R.id.roomNumberInRoomCard)
        val roomCard = view.findViewById<CardView>(R.id.roomCardInRoomCard)
        roomNumber.text = rooms[(floor*100) + position].roomNumber.toString()
        return view
    }
}