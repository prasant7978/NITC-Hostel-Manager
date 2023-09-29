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
    @POST("complaint/add")
    fun issueComplaint(@Header("auth-token") loginToken:String, @Body complaint: Complaint): Call<Boolean>

    @GET("complaint/viewOwn")
    fun viewOwnComplaint(@Header("auth-token") loginToken: String): Call<Array<Complaint>?>

    @GET("complaint/viewAll")
    fun viewAllComplaints(@Header("auth-token") loginToken: String): Call<ArrayList<Complaint>?>

    @PUT("complaint/resolveComplaint")
    fun resolveComplaint(@Header("auth-token") loginToken: String, @Query("complaintId") complaintID: Int): Call<Boolean>

    @PUT("complaint/rejectComplaint")
    fun rejectComplaint(@Header("auth-token") loginToken: String, @Query("complaintId") complaintID: Int): Call<Boolean>

    @DELETE("complaint/deleteComplaint")
    fun deleteComplaint(@Header("auth-token") loginToken: String, @Query("complaintId") complaintID: Int): Call<Boolean>
}