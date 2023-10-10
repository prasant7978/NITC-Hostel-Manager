package com.kumar.nitchostelmanager.services

import retrofit2.http.GET
import com.kumar.nitchostelmanager.models.Room
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ManageRoomsService {
    @GET("rooms/allRooms")
    fun getAllRooms(@Header("auth-token") loginToken: String, @Query("hostelID") hostelID: String): Call<ArrayList<Room>>

    @GET("rooms/availableRooms")
    fun getAvailableRooms(@Header("auth-token") loginToken: String, @Query("hostelID") hostelID: String): Call<ArrayList<Room>>

    @PUT("rooms/allocateRoom")
    fun allocateRoom(@Header("auth-token") loginToken: String, @Query("roomID") roomID: String, @Query("hostelID") hostelID: String): Call<Boolean>

    @PUT("rooms/deallocateRoom")
    fun deallocateRoom(@Header("auth-token") loginToken: String, @Query("roomID") roomID: String, @Query("hostelID") hostelID: String): Call<Boolean>

    @DELETE("rooms/deleteRoom")
    fun deleteRoom(@Header("auth-token") loginToken: String, @Query("roomID") roomID: String, @Query("hostelID") hostelID: String): Call<Boolean>

    @POST("rooms/addRoom")
    fun addRoom(@Header("auth-token") loginToken: String, @Body room: Room): Call<Boolean>

}