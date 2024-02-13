package com.kumar.nitchostelmanager.viewModel

import androidx.lifecycle.ViewModel
import com.kumar.nitchostelmanager.models.Hostel

class SharedViewModel:ViewModel() {

    var viewingWardenEmail: String? = null
    var viewingStudentRoll: String? = null
    var updatingHostelID:String? = null
    var viewingHostel: Hostel? = null
//    var availableRooms:Array<Room>? = null
    var currentFloor:Int = 1
}