package com.kumar.nitchostelmanager.services

import com.kumar.nitchostelmanager.models.Complaint
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ManageComplaintService {
    @POST("complaints/issue")
    fun issueComplaint(@Header("auth-token") loginToken:String, @Body complaint: Complaint): Call<Boolean>

    @GET("complaints/own")
    fun viewOwnComplaint(@Header("auth-token") loginToken: String): Call<Array<Complaint>?>

    @GET("complaints/all")
    fun viewAllComplaints(@Header("auth-token") loginToken: String,@Query("hostelID") hostelID:String): Call<ArrayList<Complaint>?>

    @PUT("complaints/resolve")
    fun resolveComplaint(@Header("auth-token") loginToken: String, @Query("complaintID") complaintID: Int): Call<Boolean>

    @GET("complaints/count")
    fun getComplaintsCount(@Header ("auth-token") loginToken: String,@Query("hostelID") hostelID:String):Call<Int>

    @PUT("complaints/reject")
    fun rejectComplaint(@Header("auth-token") loginToken: String, @Query("complaintID") complaintID: Int): Call<Boolean>

    @DELETE("complaints/delete")
    fun deleteComplaint(@Header("auth-token") loginToken: String, @Query("complaintID") complaintID: Int): Call<Boolean>
}