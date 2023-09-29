package com.kumar.nitchostelmanager

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kumar.nitchostelmanager.models.Complaint
import com.kumar.nitchostelmanager.services.ManageComplaintService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ManageComplaintAccess(var context: Context, private var profileViewModel: ProfileViewModel) {

    suspend fun issueComplaint(complaint: Complaint): Boolean{
        return suspendCoroutine { continuation ->
            val manageComplaintService = ServiceBuilder.buildService(ManageComplaintService::class.java)
            val requestCall = manageComplaintService.issueComplaint(profileViewModel.loginToken.toString(), complaint)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        continuation.resume(true)
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("issueComplaint","Error : $t")
                    continuation.resume(false)
                }

            })
        }
    }

    suspend fun viewOwnComplaint(): Array<Complaint>?{
        return suspendCoroutine { continuation ->
            val manageComplaintService = ServiceBuilder.buildService(ManageComplaintService::class.java)
            val requestCall = manageComplaintService.viewOwnComplaint(profileViewModel.loginToken.toString())

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
            val requestCall = manageComplaintService.viewAllComplaints(profileViewModel.loginToken.toString())

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

    suspend fun resolveComplaint(complaintId: Int): Boolean{
        return suspendCoroutine { continuation ->
            val manageComplaintService = ServiceBuilder.buildService(ManageComplaintService::class.java)
            val requestCall = manageComplaintService.resolveComplaint(profileViewModel.loginToken.toString(), complaintId)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        continuation.resume(true)
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("resolveComplaint","Error : $t")
                    continuation.resume(false)
                }

            })
        }
    }

    suspend fun rejectComplaint(complaintId: Int): Boolean{
        return suspendCoroutine { continuation ->
            val manageComplaintService = ServiceBuilder.buildService(ManageComplaintService::class.java)
            val requestCall = manageComplaintService.rejectComplaint(profileViewModel.loginToken.toString(), complaintId)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        continuation.resume(true)
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("rejectComplaint","Error : $t")
                    continuation.resume(false)
                }

            })
        }
    }

    suspend fun deleteComplaint(complaintId: Int): Boolean{
        return suspendCoroutine { continuation ->
            val manageComplaintService = ServiceBuilder.buildService(ManageComplaintService::class.java)
            val requestCall = manageComplaintService.deleteComplaint(profileViewModel.loginToken.toString(), complaintId)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        continuation.resume(true)
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("deleteComplaint","Error : $t")
                    continuation.resume(false)
                }

            })
        }
    }

}