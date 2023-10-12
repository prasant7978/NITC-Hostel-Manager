package com.kumar.nitchostelmanager.models

data class Payment(
    var paymentID: Int = 0,
    var status: String = "",
    var studentRoll: String = "",
    var date: String = "",
    var time: String = "",
    var amount: Double = 0.0,
    var billID:Int = 0
)
