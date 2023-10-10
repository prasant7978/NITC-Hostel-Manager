package com.kumar.nitchostelmanager.models

data class Student(
    var studentRoll: String = "",
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var phone: String = "",
    var parentPhone: String = "",
    var gender: String = "",
    var dob: String = "",
    var dues: Double = 0.0,
    var address: String = "",
    var course: String = "",
    var hostelID: String? = null,
    var roomNumber: Int = -1
)
