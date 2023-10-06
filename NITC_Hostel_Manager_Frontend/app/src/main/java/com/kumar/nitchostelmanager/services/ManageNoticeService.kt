package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Notice
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ManageNoticeService {

    @GET("notices/countNotices")
    fun getNoticesCount(@Header("auth-token") loginToken:String): Call<Int>

    @GET("notices/getAllNotices")
    fun getAllNotices(@Header("auth-token") loginToken:String): Call<Array<Notice>?>

    @POST("notices/addNotice")
    fun issueNotice(@Header("auth-token") loginToken: String, @Body notice: Notice): Call<Boolean>

    @DELETE("notices/deleteNotice")
    fun deleteNotice(@Header("auth-token") loginToken: String, @Body noticeId: String): Call<Boolean>
}