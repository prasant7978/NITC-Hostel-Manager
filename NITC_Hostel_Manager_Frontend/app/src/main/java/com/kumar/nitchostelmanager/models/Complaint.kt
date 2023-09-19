package com.kumar.nitchostelmanager.models

import java.sql.Date

data class Complaint(
    var complaintID: Int = 0,
    var referTo: String = "",
    var status: String = "",
    var studentRoll: String = "",
    var message: String = "",
    var date: String = "",
    var time: String = "",
){}