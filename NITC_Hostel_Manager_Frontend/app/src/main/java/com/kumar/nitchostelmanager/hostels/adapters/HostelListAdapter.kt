package com.kumar.nitchostelmanager.hostels.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kumar.nitchostelmanager.models.Hostel

class HostelListAdapter(
    var context: Context,
    var parentFragment: Fragment,
    var hostels:Array<Hostel>
) :RecyclerView.Adapter<HostelListAdapter.HostelListViewHolder>(){
    class HostelListViewHolder(val binding:): {

    }
}