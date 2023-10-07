package com.kumar.nitchostelmanager.hostels.access

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kumar.nitchostelmanager.services.ManageStudentsService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HostelDataAccess(
    var context:Context,
    var parentFragment: Fragment,
    var loginToken:String
) {

    suspend fun getHostelOccupantsCount():Int{
        return suspendCoroutine { continuation ->
            var manageStudentsService = ServiceBuilder.buildService(ManageStudentsService::class.java)
            var requestCall = manageStudentsService.getStudentsInHostelCount(loginToken.toString())
            requestCall.enqueue(object: Callback<Int> {
                override fun onResponse(
                    call: Call<Int>,
                    response: Response<Int>
                ) {
                    if(response.isSuccessful && response.body() != null){
                        continuation.resume(response.body()!!)
                    }
                    else if(response.code() == 404){
                        Toast.makeText(context,"Email already taken", Toast.LENGTH_SHORT).show()
                        continuation.resume(-1)
                    }
                    else{
                        Toast.makeText(context,"Some error occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(-1)
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Toast.makeText(context,"Error: $t", Toast.LENGTH_SHORT).show()
                    Log.d("getStudentsCount","Error : $t")
                    continuation.resume(-1)
                }
            })
        }
    }

}