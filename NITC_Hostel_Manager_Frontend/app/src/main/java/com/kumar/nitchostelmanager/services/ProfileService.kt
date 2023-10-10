package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Admin
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.models.Warden
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileService {
    @GET("students/profile")
    fun getStudentProfile(@Header("auth-token") loginToken: String): Call<Student?>

    @GET("students/getBill")
    fun getBill(@Header("auth-token") loginToken: String): Call<Double>

    @GET("admin/profile")
    fun getAdminProfile(@Header("auth-token") loginToken: String): Call<Admin?>

    @GET("warden/profile")
    fun getWardenProfile(@Header("auth-token") loginToken: String): Call<Warden?>
}