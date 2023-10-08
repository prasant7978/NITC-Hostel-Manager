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

    suspend fun getWardens(layout: ConstraintLayout):ArrayList<Warden>?{
        return suspendCoroutine { continuation ->
            var manageWardenService = ServiceBuilder.buildService(ManageWardensService::class.java)
            var requestCall = manageWardenService.getWardens(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<ArrayList<Warden>?> {
                override fun onResponse(
                    call: Call<ArrayList<Warden>?>,
                    response: Response<ArrayList<Warden>?>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(response.body())
                    }
                    else if(response.code() == 500){
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                    else{
                        Snackbar.make(layout,"No wardens found", Snackbar.LENGTH_LONG).setAction("close",
                            View.OnClickListener { }).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Warden>?>, t: Throwable) {
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    Log.d("getWardens","Error : $t")
                    continuation.resume(null)
                }

            })
        }
    }


    suspend fun addWarden(newWarden: Warden): Boolean{
        return suspendCoroutine { continuation ->
            var manageWardenService = ServiceBuilder.buildService(ManageWardensService::class.java)
            var requestCall = manageWardenService.addWarden(profileViewModel.loginToken.toString(),newWarden)

            requestCall.enqueue(object: Callback<Warden?> {
                override fun onResponse(
                    call: Call<Warden?>,
                    response: Response<Warden?>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(true)
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Warden?>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("addWarden","Error : $t")
                    continuation.resume(false)
                }
            })
        }
    }

    suspend fun updateWarden(wardenEmail: String,newWarden: Warden): Warden?{
        return suspendCoroutine { continuation ->
            var manageWardenService = ServiceBuilder.buildService(ManageWardensService::class.java)
            var requestCall = manageWardenService.updateWarden(profileViewModel.loginToken.toString(),wardenEmail,newWarden)
            requestCall.enqueue(object: Callback<Warden?> {
                override fun onResponse(
                    call: Call<Warden?>,
                    response: Response<Warden?>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(response.body())
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Warden?>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("updateWarden","Error : $t")
                    continuation.resume(null)
                }
            })
        }
    }



    suspend fun deleteWarden(wardenEmail:String):Boolean{
        return suspendCoroutine { continuation ->
            var manageWardenService = ServiceBuilder.buildService(ManageWardensService::class.java)
            var requestCall = manageWardenService.deleteWarden(profileViewModel.loginToken.toString(),wardenEmail)

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