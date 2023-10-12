package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Hostel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface HostelsService {

    @POST("hostels/add")
    fun addHostel(@Header ("auth-token") loginToken:String, @Body newHostel:Hostel): Call<Boolean>

    @POST("hostels/update")
    fun updateHostel(@Header ("auth-token") loginToken:String, @Body newHostel:Hostel): Call<Boolean>

    @GET("hostels/all")
    fun getAllHostels(@Header ("auth-token") loginToken: String): Call<ArrayList<Hostel>>


    @GET("hostels/allNames")
    fun getAllHostelsNames(@Header ("auth-token") loginToken: String): Call<Array<String>>

    @GET("hostels/details")
    fun getHostelDetails(@Header ("auth-token") loginToken: String, @Query("hostelID") hostelID:String): Call<Hostel>

    @DELETE("hostels/delete")
    fun deleteHostel(@Header ("auth-token") loginToken: String, @Query("hostelID") hostelID: String): Call<Boolean>

}