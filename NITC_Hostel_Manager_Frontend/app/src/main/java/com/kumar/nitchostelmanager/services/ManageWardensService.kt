package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.models.Warden
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ManageWardensService {

    @GET("admin/wardens/getAll")
    fun getWardens(loginToken:String): Call<Array<Warden>>



    @POST("admin/wardens/add")
    fun addWarden(@Header("auth-token") loginToken: String, @Body warden: Warden):Call<Warden?>

    @POST("admin/wardens/update")
    fun updateWarden(@Header("auth-token") loginToken: String, @Query("wardenEmail") wardenEmail:String, @Body warden: Warden):Call<Warden?>


    @POST("admin/wardens/delete")
    fun deleteWarden(@Header("auth-token") loginToken: String, @Query("wardenEmail") wardenEmail:String):Call<Boolean>

}