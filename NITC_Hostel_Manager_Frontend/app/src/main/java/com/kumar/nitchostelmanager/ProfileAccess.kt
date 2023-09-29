package com.kumar.nitchostelmanager

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kumar.nitchostelmanager.models.Admin
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.services.ProfileService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfileAccess(var context: Context, private var profileViewModel: ProfileViewModel) {

    suspend fun getStudentProfile(): Student?{
        return suspendCoroutine { continuation ->
            val profileService = ServiceBuilder.buildService(ProfileService::class.java)
            val requestCall = profileService.getStudentProfile(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<Student?>{
                override fun onResponse(call: Call<Student?>, response: Response<Student?>) {
                    if(response.isSuccessful)
                        continuation.resume(response.body())
                    else{
                        Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Student?>, t: Throwable) {
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    Log.d("login","Error : $t")
                    continuation.resume(null)
                }

            })
        }
    }

    suspend fun getAdminProfile(): Admin?{
        return suspendCoroutine { continuation ->
            val profileService = ServiceBuilder.buildService(ProfileService::class.java)
            val requestCall = profileService.getAdminProfile(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<Admin?>{
                override fun onResponse(call: Call<Admin?>, response: Response<Admin?>) {
                    if(response.isSuccessful)
                        continuation.resume(response.body())
                    else{
                        Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Admin?>, t: Throwable) {
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    Log.d("login","Error : $t")
                    continuation.resume(null)
                }

            })
        }
    }

    suspend fun getWardenProfile(): Warden?{
        return suspendCoroutine { continuation ->
            val profileService = ServiceBuilder.buildService(ProfileService::class.java)
            val requestCall = profileService.getWardenProfile(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<Warden?>{
                override fun onResponse(call: Call<Warden?>, response: Response<Warden?>) {
                    if(response.isSuccessful)
                        continuation.resume(response.body())
                    else{
                        Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Warden?>, t: Throwable) {
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    Log.d("login","Error : $t")
                    continuation.resume(null)
                }

            })
        }
    }

}