package com.kumar.nitchostelmanager.profile.access

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kumar.nitchostelmanager.models.Admin
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.services.ProfileService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
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

    suspend fun updatePassword(newPassword:String): Boolean{
        return suspendCoroutine { continuation ->
            val profileService = ServiceBuilder.buildService(ProfileService::class.java)
            val requestCall = profileService.updatePassword(profileViewModel.loginToken.toString(),newPassword)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful && response.body() == true)
                        continuation.resume(true)
                    else{
                        Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    Log.d("login","Error : $t")
                    continuation.resume(false)
                }

            })
        }
    }

    suspend fun getDue(): Double{
        return suspendCoroutine { continuation ->
            val profileService = ServiceBuilder.buildService(ProfileService::class.java)
            val requestCall = profileService.getDue(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<Double>{
                override fun onResponse(call: Call<Double>, response: Response<Double>) {
                    if(response.isSuccessful && response.body() != null) {
                        Log.d("totalDue", response.body().toString())
                        continuation.resume(response.body()!!)
                    }
                    else {
                        Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(0.0)
                    }
                }

                override fun onFailure(call: Call<Double>, t: Throwable) {
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    Log.d("getDue","Error : $t")
                    continuation.resume(0.0)
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
                    if(response.isSuccessful && response.body() != null)
                        continuation.resume(response.body())
                    else{
                        Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Admin?>, t: Throwable) {
                    Toast.makeText(context,"Error in getting admin profile: $t",Toast.LENGTH_SHORT).show()
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