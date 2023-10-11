package com.kumar.nitchostelmanager.models

data class Notice(
    var date: String = "",
    var heading: String = "",
    var message: String = "",
    var hostelID:String? = null,
    var issuerID:String? = null
)
