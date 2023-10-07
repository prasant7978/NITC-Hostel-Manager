package com.kumar.nitchostelmanager.complaints.access

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kumar.nitchostelmanager.models.Complaint
import com.kumar.nitchostelmanager.services.ManageComplaintService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ComplaintsDataAccess(
    var context:Context,
    var parentFragment: Fragment,
    var loginToken:String
) {

    suspend fun viewOwnComplaint(): Array<Complaint>?{
        return suspendCoroutine { continuation ->
            val manageComplaintService = ServiceBuilder.buildService(ManageComplaintService::class.java)
            val requestCall = manageComplaintService.viewOwnComplaint(loginToken.toString())

            requestCall.enqueue(object: Callback<Array<Complaint>?>{
                override fun onResponse(call: Call<Array<Complaint>?>, response: Response<Array<Complaint>?>) {
                    if(response.isSuccessful){
                        continuation.resume(response.body())
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Array<Complaint>?>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("viewOwnComplaint","Error : $t")
                    continuation.resume(null)
                }
            })
        }
    }

    suspend fun viewAllComplaints(): ArrayList<Complaint>?{
        return suspendCoroutine { continuation ->
            val manageComplaintService = ServiceBuilder.buildService(ManageComplaintService::class.java)
            val requestCall = manageComplaintService.viewAllComplaints(loginToken.toString())

            requestCall.enqueue(object: Callback<ArrayList<Complaint>?>{
                override fun onResponse(call: Call<ArrayList<Complaint>?>, response: Response<ArrayList<Complaint>?>) {
                    if(response.isSuccessful){
                        continuation.resume(response.body())
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Complaint>?>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("viewOwnComplaint","Error : $t")
                    continuation.resume(null)
                }
            })
        }
    }

    suspend fun getComplaintsCount(): Int{
        return suspendCoroutine { continuation ->
            val manageComplaintService = ServiceBuilder.buildService(ManageComplaintService::class.java)
            val requestCall = manageComplaintService.getComplaintsCount(loginToken.toString())

            requestCall.enqueue(object: Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if(response.isSuccessful && response.body() != null){
                        continuation.resume(response.body()!!)
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(0)
                    }
                }
                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Toast.makeText(context,"Error: $t", Toast.LENGTH_SHORT).show()
                    Log.d("issueComplaint","Error : $t")
                    continuation.resume(0)
                }

            })
        }
    }

}