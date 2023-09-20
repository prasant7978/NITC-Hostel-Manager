package com.kumar.nitchostelmanager.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("auth/login/admin")
    fun loginAdmin(@Body loginBody:HashMap<String,String>): Call<Boolean>

    @POST("auth/login/student")
    fun loginStudent(@Body loginBody:HashMap<String,String>): Call<Boolean>

    @POST("auth/login/warden")
    fun loginWarden(@Body loginBody:HashMap<String,String>): Call<Boolean>

}