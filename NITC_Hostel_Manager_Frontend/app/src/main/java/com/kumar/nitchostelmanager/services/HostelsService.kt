package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Hostel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface HostelsService {

    @POST("admin/hostels/add")
    fun addHostel(@Header ("auth-token") loginToken:String, @Body newHostel:Hostel):Call<Boolean>

    @POST("admin/hostels/update")
    fun updateHostel(@Header ("auth-token") loginToken:String, @Body newHostel:Hostel):Call<Boolean>

    @GET("admin/hostels/all")
    fun getHostels(@Header ("auth-token") loginToken: String):Call<Array<Hostel>>


    @GET("admin/hostels/find")
    fun getHostelDetails(@Header ("auth-token") loginToken: String,@Query("hostelID") hostelID:String):Call<Hostel>



}