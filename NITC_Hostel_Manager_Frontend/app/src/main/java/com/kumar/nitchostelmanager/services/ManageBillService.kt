package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Payment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ManageBillService {
    @POST("student/payBill")
    fun issueBill(@Header("auth-token") loginToken: String, @Body map: HashMap<String, String>): Call<Boolean>

}