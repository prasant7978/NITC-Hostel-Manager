package com.kumar.nitchostelmanager.models

data class Notice(
    var noticeID: Int = 0,
    var date: String = "",
    var time: String = "",
    var message: String = "",
    var hostelID: String = ""
){}
