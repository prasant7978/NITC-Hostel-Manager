package com.kumar.nitchostelmanager.wardens.access

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.services.ManageWardensService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ManageWardensAccess(
    var context: Context,
    var parentFragment: Fragment,
    var profileViewModel: ProfileViewModel
) {
    suspend fun addWarden(newWarden: Warden): Boolean{
        return suspendCoroutine { continuation ->
            var manageWardenService = ServiceBuilder.buildService(ManageWardensService::class.java)
            var requestCall = manageWardenService.addWarden(profileViewModel.loginToken.toString(),newWarden)

            requestCall.enqueue(object: Callback<Boolean> {
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(true)
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("addWarden","Error : $t")
                    continuation.resume(false)
                }
            })
        }
    }

    suspend fun updateWarden(wardenEmail: String, newWarden: Warden,hostelID:String): Boolean{
        return suspendCoroutine { continuation ->
            var manageWardenService = ServiceBuilder.buildService(ManageWardensService::class.java)
            var requestCall = manageWardenService.updateWarden(profileViewModel.loginToken.toString(), wardenEmail, newWarden,hostelID)
            requestCall.enqueue(object: Callback<Boolean> {
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(true)
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("updateWarden","Error : $t")
                    continuation.resume(false)
                }
            })
        }
    }

    suspend fun getWardenDetails(wardenEmail: String): Warden?{
        return  suspendCoroutine { continuation ->
            var manageWardensService = ServiceBuilder.buildService(ManageWardensService::class.java)
            var requestCall = manageWardensService.getWarden(profileViewModel.loginToken.toString(), wardenEmail)

            requestCall.enqueue(object : Callback<Warden?> {
                override fun onResponse(call: Call<Warden?>, response: Response<Warden?>) {
                    if(response.isSuccessful && response.body() != null){
                        continuation.resume(response.body())
                    }
                    else{
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Warden?>, t: Throwable) {
                    Toast.makeText(context,"Error in getting warden details",Toast.LENGTH_SHORT).show()
                    Log.d("getStudent","Error in getting warden details : $t")
                    continuation.resume(null)
                }

            })
        }
    }

    suspend fun deleteWarden(wardenEmail:String,hostelID:String):Boolean{
        return suspendCoroutine { continuation ->
            var manageWardenService = ServiceBuilder.buildService(ManageWardensService::class.java)
            var requestCall = manageWardenService.deleteWarden(profileViewModel.loginToken.toString(),wardenEmail,hostelID)

            requestCall.enqueue(object: Callback<Boolean> {
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(true)
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("deleteWarden","Error : $t")
                    continuation.resume(false)
                }
            })
        }
    }


}