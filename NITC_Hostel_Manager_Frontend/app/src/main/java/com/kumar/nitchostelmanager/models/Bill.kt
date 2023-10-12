package com.kumar.nitchostelmanager.models

data class Bill(
    var billID: String? = null,
    var studentRoll: String? = null,
    var amount: Double = 0.0,
    var paid: Byte = 0,
    var billType: String? = null,
    var billMonth: String? = null,
    var billYear: String? = null,
    var paymentID: Int = -1,
    var paymentDate: String? = null,
    var paymentTime: String? = null,
    var hostelID: String? = null,
)
