package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Student
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ManageStudentsService {

    @GET("admin/students/count")
    fun getStudentsCount(@Header("auth-token") loginToken:String): Call<HashMap<String,Int>>

    @GET("warden/students/count")
    fun getOccupantsCount(@Header("auth-token") loginToken:String, @Query ("hostelID") hostelID:String): Call<Int>


    @GET("warden/students/all")
    fun getOccupants(@Header("auth-token") loginToken:String, @Query ("hostelID") hostelID:String): Call<List<Student>>


    @GET("admin/students/all")
    fun getAllStudents(@Header("auth-token") loginToken: String):Call<ArrayList<Student>?>


    @GET("admin/students/girls")
    fun getGirls(@Header("auth-token") loginToken: String):Call<ArrayList<Student>?>


    @GET("admin/students/boys")
    fun getBoys(@Header("auth-token") loginToken: String):Call<ArrayList<Student>?>

    @POST("admin/students/add")
    fun addStudent(@Header("auth-token") loginToken: String,@Body student: Student):Call<Boolean>

    @POST("admin/students/update")
    fun updateStudent(@Header("auth-token") loginToken: String,@Query("studentRoll") studentRoll:String,@Body student: Student):Call<Student?>

    @DELETE("admin/students/delete")
    fun deleteStudent(@Header("auth-token") loginToken: String,@Query("studentRoll") studentRoll:String):Call<Boolean>

    @GET("admin/students/details")
    fun getStudent(@Header("auth-token") loginToken: String, @Query("studentRoll") viewingStudentRoll: String): Call<Student>

}