package com.kumar.nitchostelmanager

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kumar.nitchostelmanager.models.Payment
import com.kumar.nitchostelmanager.services.ManageBillService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PaymentAccess(var context: Context, private var profileViewModel: ProfileViewModel) {
    suspend fun issueBill(payment: HashMap<String, String>): Boolean{
        return suspendCoroutine { continuation ->
            val paymentService = ServiceBuilder.buildService(ManageBillService::class.java)
            val requestCall = paymentService.issueBill(profileViewModel.loginToken.toString(), payment)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful)
                        continuation.resume(true)
                    else{
                        Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }

                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    Log.d("issueBill","Error : $t")
                    continuation.resume(false)
                }
            })
        }
    }
}