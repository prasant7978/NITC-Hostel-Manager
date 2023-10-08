package com.kumar.nitchostelmanager.models

data class Hostel(
    var hostelID: String = "",
    var capacity: Int = 0,
    var charges: Int = 0,
    var totalDues: Double = 0.0,
    var occupantsGender:String? = null,
    var wardenEmail: String? = null
){}
