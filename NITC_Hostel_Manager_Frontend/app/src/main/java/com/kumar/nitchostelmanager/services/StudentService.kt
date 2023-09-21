package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Complaint
import com.kumar.nitchostelmanager.models.Student
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface StudentService {

    @GET("complaint/add")
    fun addComplaint(@Header ("auth-token") loginToken:String,@Body complaint:Complaint): Call<Boolean>

    @GET("complaint/viewOwn")
    fun viewOwnComplaint(@Header ("auth-token") loginToken: String):Call<Array<Complaint>>

    @GET("student/profile")
    fun getProfile(@Header("auth-token") loginToken: String):Call<Student>
}