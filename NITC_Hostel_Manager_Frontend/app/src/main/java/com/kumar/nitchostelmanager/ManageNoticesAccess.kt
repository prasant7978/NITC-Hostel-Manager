package com.kumar.nitchostelmanager

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kumar.nitchostelmanager.services.ManageNoticeService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ManageNoticesAccess(
    var context:Context,
    var loginToken:String
) {

    suspend fun getNoticesCount():Int{
        return suspendCoroutine { continuation ->
            var manageNoticeService = ServiceBuilder.buildService(ManageNoticeService::class.java)
            var requestCall = manageNoticeService.getNoticesCount(loginToken)
            requestCall.enqueue(object:Callback<Int>{
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if(response.isSuccessful && response.body() != null){
                        continuation.resume(response.body()!!)
                    }else{
                        Toast.makeText(context,"Error occurred in getting notices count",Toast.LENGTH_SHORT).show()
                        continuation.resume(0)
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Log.d("noticesCount","Error: $t")
                    Toast.makeText(context,"Error occurred in getting notices count: $t",Toast.LENGTH_SHORT).show()
                    continuation.resume(0)
                }

            })
        }
    }

}