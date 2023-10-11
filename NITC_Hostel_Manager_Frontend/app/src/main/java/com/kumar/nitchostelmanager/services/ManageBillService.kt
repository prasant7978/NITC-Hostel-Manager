package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Bill
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ManageBillService {
    @POST("bill/generateBill")
    fun generateBill(@Header("auth-token") loginToken: String, @Body bill: Bill): Call<Boolean>

    @GET("bill/getAllOwnBills")
    fun getAllOwnBills(@Header("auth-token") loginToken: String): Call<ArrayList<Bill>>

    @GET("bill/getAllBills")
    fun getAllBills(@Header("auth-token") loginToken: String): Call<ArrayList<Bill>>
}