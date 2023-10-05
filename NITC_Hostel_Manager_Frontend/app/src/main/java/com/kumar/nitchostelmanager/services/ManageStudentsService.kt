package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Student
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ManageStudentsService {

    @GET("admin/students/count")
    fun getStudentsCount(@Header("auth-token") loginToken:String): Call<HashMap<String,Int>>

    @GET("admin/students/all")
    fun getAllStudents(@Header("auth-token") loginToken: String):Call<Array<Student>?>

    @POST("admin/students/add")
    fun addStudent(@Header("auth-token") loginToken: String,@Body student: Student):Call<Boolean>

    @POST("admin/students/update")
    fun updateStudent(@Header("auth-token") loginToken: String,@Query("studentRoll") studentRoll:String,@Body student: Student):Call<Student?>

    @POST("admin/students/delete")
    fun deleteStudent(@Header("auth-token") loginToken: String,@Query("studentRoll") studentRoll:String):Call<Boolean>

}