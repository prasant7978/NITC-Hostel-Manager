package com.kumar.nitchostelmanager.complaints.access

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kumar.nitchostelmanager.models.Complaint
import com.kumar.nitchostelmanager.services.ManageComplaintService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
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