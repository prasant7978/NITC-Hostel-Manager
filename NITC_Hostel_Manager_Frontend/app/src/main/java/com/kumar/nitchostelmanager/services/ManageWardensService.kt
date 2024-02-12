package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.models.Warden
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ManageWardensService {

    @GET("admin/wardens/all")
    fun getWardens(@Header ("auth-token") loginToken:String): Call<ArrayList<Warden>?>

    @GET("admin/wardens/getDetails")
    fun getWarden(@Header("auth-token") loginToken: String, @Query("wardenEmail") wardenEmail: String): Call<Warden?>

    @GET("admin/wardens/count")
    fun getWardensCount(@Header ("auth-token") loginToken:String): Call<Int>

    @POST("admin/wardens/add")
    fun addWarden(@Header("auth-token") loginToken: String, @Body warden: Warden):Call<Boolean>

    @PUT("admin/wardens/update")
    fun updateWarden(@Header("auth-token") loginToken: String, @Query("wardenEmail") wardenEmail:String, @Body warden: Warden):Call<Warden?>

    @DELETE("admin/wardens/delete")
    fun deleteWarden(@Header("auth-token") loginToken: String, @Query("wardenEmail") wardenEmail:String):Call<Boolean>

}