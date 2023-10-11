package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Payment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PaymentService {
    @POST("payment/student/payDues")
    fun payDues(@Header("auth-token") loginToken: String, @Body payment: Payment): Call<Boolean>
}