package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Admin
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.models.Warden
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileService {
    @GET("student/profile")
    fun getStudentProfile(@Header("auth-token") loginToken: String): Call<Student?>

    @GET("admin/profile")
    fun getAdminProfile(@Header("auth-token") loginToken: String): Call<Admin?>

    @GET("warden/profile")
    fun getWardenProfile(@Header("auth-token") loginToken: String): Call<Warden?>
}