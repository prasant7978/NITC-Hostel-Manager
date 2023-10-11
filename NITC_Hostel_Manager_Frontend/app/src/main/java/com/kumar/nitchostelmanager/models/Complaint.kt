package com.kumar.nitchostelmanager.models

import java.sql.Date

data class Complaint(
    var complaintID: Int = 0,
    var status: String = "",
    var studentRoll: String = "",
    var message: String = "",
    var date: String = "",
    var time: String = "",
    var hostelID: String = "",
    var roomNumber: Int = 0
){}