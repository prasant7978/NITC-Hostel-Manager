package com.kumar.nitchostelmanager.viewModel

import androidx.lifecycle.ViewModel
import com.kumar.nitchostelmanager.models.Room

class SharedViewModel:ViewModel() {

    var viewingWardenEmail: String? = null
    var viewingStudentRoll: String? = null
    var updatingHostelID:String? = null
    var viewingHostelID:String? = null
//    var availableRooms:Array<Room>? = null
    var firstFloorRooms = arrayListOf<Room>()
    var secondFloorRooms = arrayListOf<Room>()
    var groundFloorRooms = arrayListOf<Room>()
    var thirdFloorRooms = arrayListOf<Room>()
    var fourthFloorRooms = arrayListOf<Room>()
    var currentFloor:Int = 1
}