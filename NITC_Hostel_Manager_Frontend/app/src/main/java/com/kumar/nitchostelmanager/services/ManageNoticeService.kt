package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Notice
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface ManageNoticeService {


    @GET("notices/count")
    fun getNoticesCount(@Header("auth-token") loginToken:String): Call<Int>
    @GET("notices/all")
    fun getNotices(@Header("auth-token") loginToken:String): Call<Array<Notice>?>

    @GET("notices/add")
    fun addNotice(@Header("auth-token") loginToken: String, @Body notice: Notice): Call<Boolean>
}