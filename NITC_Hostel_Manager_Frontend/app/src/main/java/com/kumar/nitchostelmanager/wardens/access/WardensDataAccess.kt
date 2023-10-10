package com.kumar.nitchostelmanager.wardens.access

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.services.ManageWardensService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WardensDataAccess(
    var context: Context,
    var loginToken:String
) {


    suspend fun getWardensCount(layout: ConstraintLayout):Int{
        return suspendCoroutine { continuation ->
            var manageWardenService = ServiceBuilder.buildService(ManageWardensService::class.java)
            var requestCall = manageWardenService.getWardensCount(loginToken.toString())

            requestCall.enqueue(object: Callback<Int> {
                override fun onResponse(
                    call: Call<Int>,
                    response: Response<Int>
                ) {
                    if(response.isSuccessful && response.body() != null){
                        continuation.resume(response.body()!!)
                    }
                    else if(response.code() == 500){
                        Toast.makeText(context,"Some error occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(-1)
                    }
                    else{
                        Snackbar.make(layout,"No wardens found", Snackbar.LENGTH_LONG).setAction("close",
                            View.OnClickListener { }).show()
                        continuation.resume(-1)
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    Log.d("getWardens","Error : $t")
                    continuation.resume(-1)
                }

            })
        }
    }
    suspend fun getWardens(layout: ConstraintLayout):ArrayList<Warden>?{
        return suspendCoroutine { continuation ->
            var manageWardenService = ServiceBuilder.buildService(ManageWardensService::class.java)
            var requestCall = manageWardenService.getWardens(loginToken.toString())

            requestCall.enqueue(object: Callback<ArrayList<Warden>?> {
                override fun onResponse(
                    call: Call<ArrayList<Warden>?>,
                    response: Response<ArrayList<Warden>?>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(response.body())
                    }
                    else if(response.code() == 500){
                        Toast.makeText(context,"Some error occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                    else{
                        Snackbar.make(layout,"No wardens found", Snackbar.LENGTH_LONG).setAction("close",
                            View.OnClickListener { }).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Warden>?>, t: Throwable) {
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    Log.d("getWardens","Error : $t")
                    continuation.resume(null)
                }

            })
        }
    }

}