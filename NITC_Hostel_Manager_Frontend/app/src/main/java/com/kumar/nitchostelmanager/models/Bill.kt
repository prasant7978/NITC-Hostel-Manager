package com.kumar.nitchostelmanager.models

data class Bill(
    var billID: String = "",
    var studentRoll: String = "",
    var amount: Double = 0.0,
    var paid: Boolean = false,
    var billMonth: String = "",
    var billYear: String = "",
    var paymentID: Int = 0,
    var paymentDate: String = "",
    var hostelID: String = ""
)
